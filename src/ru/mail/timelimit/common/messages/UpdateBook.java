package ru.mail.timelimit.common.messages;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UpdateBook")
public class UpdateBook
{

    public static String toXml(UpdateBook updateBook) throws JAXBException
    {
        return null;
    }
}
