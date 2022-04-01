package com.acpreda.sprint10.db.doc;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

class MssqlTest {

    @DisplayName("Should create a Markdown document")
    @Test
    void should_create_a_markdown_document() throws Exception {
        SQLServerDataSource dataSource = null;
        dataSource = new SQLServerDataSource();
        dataSource.setURL("jdbc:sqlserver://44.192.57.231;user=anhportal;password=ABC123abc;databaseName=anhportal_learning;encrypt=true;trustServerCertificate=true");

        String[] tableFilters = new String[]{
                "a%", "ca%", "dja%", "flex%", "home%", "news%", "normativity%", "round%", "site%", "util%",
                "wagtail_embed%", "wagtailadmin%", "wagtailcore%", "wagtaildocs%", "wagtailemb%", "wagtailr%",
                "wagtailr%", "wagtailu%"
        };
        MssqlMetadataProvider metadataProvider = new MssqlMetadataProvider(dataSource, "dbo", tableFilters);
        MarkdownFormat format = new MarkdownFormat();
        Database database = metadataProvider.getDatabase();
        byte[] bytes = format.format(database);
        Assertions.assertNotNull(bytes);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(new File("database.md"), bytes);

        String updateScript = metadataProvider.updateCommentsScript(database);
        Assertions.assertNotNull(updateScript);
        System.out.println(updateScript);
        FileUtils.writeByteArrayToFile(
                new File("database.sql"),
                updateScript.getBytes(StandardCharsets.UTF_8));
    }

}