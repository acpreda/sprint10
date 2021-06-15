package com.acpreda.sprint10.db.doc;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class MarkdownFormat extends DocumentFormat {

    private final Configuration freemarker;

    public MarkdownFormat() {
        freemarker = new Configuration(Configuration.VERSION_2_3_29);
        try {
            freemarker.setDirectoryForTemplateLoading(new File("/Users/acpreda/Projects/sprint10/sprint10-db-doc/src/main/resources/formats"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] format(Database database) {
        try {
            Template main = freemarker.getTemplate("main.ftl");
            Writer writer = new StringWriter();
            main.process(database, writer);
            return writer.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
