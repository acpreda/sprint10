package com.acpreda.sprint10.inventory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class JdbcInventoryManagerTest {

    private static HikariConfig config = new HikariConfig();
    private static DataSource dataSource;
    private static ZoneId zoneId;

    @BeforeAll
    static void setUp() {
        config.setJdbcUrl("jdbc:postgresql://ensdp.acpreda.com/ensdp");
        config.setUsername("ensdp");
        config.setPassword("FlaiteWorld!!");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        zoneId = ZoneId.of("America/Bogota");
    }

    @Test
    void accounts() {
        var manager = new JdbcInventoryManager(dataSource);
        manager.createAccount("1", "COP", false, true, "Bancos");
        manager.createAccount("101", "COP", false, false, "Davivienda ****0860");
        manager.createAccount("3", "COP", false, true, "Ingresos");
        manager.createAccount("301", "COP", false, false, "Ingresos por ventas");
    }

    @Test
    void transacciones() {
        var manager = new JdbcInventoryManager(dataSource);
        var banco = manager.account("101");
        var ingresosPorVentas = manager.account("301");
        var ahora = ZonedDateTime.now(zoneId);
        var venta = TransactionBuilder
                .start(1L, zoneId, "Venta")
                .with(banco, new BigDecimal("20000"), ahora)
                .with(ingresosPorVentas, new BigDecimal("-20000"), ahora)
                .build();
        manager.saveTransaction(venta);
    }
}