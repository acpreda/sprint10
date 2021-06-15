package com.acpreda.sprint10.db.doc;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

class OracleTest {

    @DisplayName("Should create a Markdown document")
    @Test
    void should_create_a_markdown_document() throws Exception {
        OracleDataSource oracleDS = null;
        oracleDS = new OracleDataSource();
        oracleDS.setURL("jdbc:oracle:thin:@192.168.106.56:1521:DBPRUEBA");
        oracleDS.setUser("RUAU");
        oracleDS.setPassword("RUAU");

        OracleMetadataProvider metadataProvider = new OracleMetadataProvider(oracleDS, "RUAU");
        MarkdownFormat format = new MarkdownFormat();

        byte[] bytes = format.format(metadataProvider.getDatabase());
        Assertions.assertNotNull(bytes);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(new File("/tmp/database.md"), bytes);

    }

}