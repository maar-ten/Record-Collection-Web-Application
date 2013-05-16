package nl.laurs.persistance.hibernateImpl;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Only way I found to enforce the UTF-8 character set on the create table statements of Hibernate
 *
 * @author: Maarten
 */
public class MyHibernateSQLDialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + "  DEFAULT CHARSET=utf8";
    }
}
