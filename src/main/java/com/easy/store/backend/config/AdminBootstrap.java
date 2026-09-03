package com.easy.store.backend.config;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Crea el usuario administrador por defecto al arrancar la aplicación, si aún
 * no existe uno con ese username. Sigue el mismo patrón que el usuario ghost
 * (EASY_STORE_GHOST_USER/PASSWORD): las credenciales vienen de variables de
 * entorno, no están hardcodeadas en el código ni en las migraciones, y el
 * password se hashea con el mismo {@link PasswordEncoder} que usa el resto de
 * la aplicación (BCrypt).
 * <p>
 * Sin esto, no había ninguna forma de obtener el primer usuario ADMIN salvo
 * insertándolo a mano en la base de datos: el endpoint que permite cambiar el
 * rol de un usuario (PUT /api/v1/user) ya exige rol ADMIN u OWNER, así que el
 * primer admin no se puede crear por la propia API (problema circular).
 * <p>
 * Si {@code easy.store.admin.user} o {@code easy.store.admin.password} no
 * están configurados, este bootstrap no hace nada (no falla el arranque).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private static final String ADMIN_ROLE = "admin";

    @Value("${easy.store.admin.user:}")
    private String adminUsername;

    @Value("${easy.store.admin.password:}")
    private String adminPassword;

    @Value("${easy.store.admin.name:Admin}")
    private String adminName;

    @Value("${easy.store.admin.last-name:User}")
    private String adminLastName;

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountHasUserRepository accountHasUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!StringUtils.hasText(adminUsername) || !StringUtils.hasText(adminPassword)) {
            log.info("ACCION ADMIN_BOOTSTRAP -> EASY_STORE_ADMIN_USER/PASSWORD no configurados, se omite la creación del admin por defecto");
            return;
        }

        Optional<User> existingAdmin = userRepository.findByUsername(adminUsername);
        if (existingAdmin.isPresent()) {
            log.info("ACCION ADMIN_BOOTSTRAP -> El usuario admin por defecto ya existe, no se hace nada");
            return;
        }

        Optional<Role> optAdminRole = roleRepository.findByName(ADMIN_ROLE);
        if (optAdminRole.isEmpty()) {
            log.warn("ACCION ADMIN_BOOTSTRAP -> No se encontró el rol 'admin', no se puede crear el admin por defecto");
            return;
        }

        log.info("ACCION ADMIN_BOOTSTRAP -> Creando usuario admin por defecto: {}", adminUsername);

        User admin = User.builder()
                .username(adminUsername)
                .name(adminName)
                .lastName(adminLastName)
                .password(passwordEncoder.encode(adminPassword))
                .state("active")
                .role(optAdminRole.get())
                .build();
        admin = userRepository.create(admin);

        Account account = Account.builder()
                .name("Cuenta administrativa")
                .description("Cuenta por defecto del usuario administrador")
                .imageName("store.png")
                .state("active")
                .build();
        account = accountRepository.create(account);

        accountHasUserRepository.create(AccountHasUser.builder()
                .id(AccountHasUserId.builder()
                        .userId(admin.getId())
                        .accountId(account.getId())
                        .build()
                )
                .userId(admin)
                .accountId(account)
                .state("active")
                .build()
        );

        log.info("ACCION ADMIN_BOOTSTRAP -> Usuario admin por defecto creado con éxito");
    }

}
