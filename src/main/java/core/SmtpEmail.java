package core;

// File Name SendEmail.java

import org.apache.log4j.Logger;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmtpEmail {

    private static final String attachMentPath = null;
    private final static Logger log = Logger.getLogger(SmtpEmail.class);
    // define somewhere the icalendar date format
    private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");

    public static void sendEmail(final String fromEmailAddress, final String password, String toEmailAddress,
                                 String subject, String body) throws AddressException, MessagingException {
        sendEmailWithAttachment(fromEmailAddress, password, toEmailAddress, subject, body, null);
    }

    public static void sendEmailWithAttachment(final String fromEmailAddress, final String password,
                                               String toEmailAddress, String subject, String body, String attachmentPath)
            throws AddressException, MessagingException {
        Session session = getSession(fromEmailAddress, password);
        // Create a MimeMessage object.
        MimeMessage message = getMailMessage(session, fromEmailAddress, toEmailAddress, subject, body, attachmentPath);
        Transport.send(message);
        MyLogger.log.info(String.format("Successfully sent email with subject %s.", subject));
    }

    public static void putMailInInbox(final String fromEmailAddress, final String password, String toEmailAddress,
                                      String subject, String body) throws AddressException, MessagingException {
        putMailInInboxWithAttachment(fromEmailAddress, password, toEmailAddress, subject, body, null);
    }

    public static void putMailInInboxWithAttachment(final String fromEmailAddress, final String password,
                                                    String toEmailAddress, String subject, String body, String attachmentPath)
            throws AddressException, MessagingException {
        putMailInFolderWithAttachment(fromEmailAddress, password, toEmailAddress, subject, body, attachmentPath,
                TOnlineFolder.INBOX);
    }

    public static void putMailInFolder(final String fromEmailAddress, final String password, String toEmailAddress,
                                       String subject, String body, TOnlineFolder foldername) throws MessagingException {
        putMailInFolderWithAttachment(fromEmailAddress, password, toEmailAddress, subject, body, null, foldername);
    }

    public static void putMailInFolderWithAttachment(final String fromEmailAddress, final String password,
                                                     String toEmailAddress, String subject, String body, String attachmentPath, TOnlineFolder foldername)
            throws MessagingException {
        Session session = getSession(toEmailAddress, password);
        // Create a default MimeMessage object.
        MimeMessage message = getMailMessage(session, fromEmailAddress, toEmailAddress, subject, body, attachmentPath);
        try {
            Store store = session.getStore("imaps");
            store.connect("secureimap.ver.sul.t-online.de", toEmailAddress, password);
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            store.close();
            MyLogger.log.info(String.format("Successfully put email with subject %s in " + foldername + ": ", subject));
        } catch (MessagingException e) {
            MyLogger.log.info("could not open folder " + foldername + ": " + e.getMessage());
            throw e;
        }
    }

    public static void sendMeetingInvite(final String fromEmailAddress, final String password, String toEmailAddress,
                                         String subject) throws Exception {
        Session session = getSession(fromEmailAddress, password);
        try {
            // register the text/calendar mime type
            MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap) MimetypesFileTypeMap.getDefaultFileTypeMap();
            mimetypes.addMimeTypes("text/calendar ics ICS");
            // register the handling of text/calendar mime type
            MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
            mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmailAddress));
            message.setSubject(subject);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));
            // Create an alternative Multipart
            Multipart multipart = new MimeMultipart("alternative");
            // part 1, html text
            MimeBodyPart descriptionPart = new MimeBodyPart();
            String content = "<font size=\"2\">simple meeting invitation</font>";
            descriptionPart.setContent(content, "text/html; charset=utf-8");
            BodyPart messageBodyPart = descriptionPart;
            multipart.addBodyPart(messageBodyPart);
            // Add part two, the calendar
            BodyPart calendarPart = buildCalendarPart(fromEmailAddress);
            multipart.addBodyPart(calendarPart);
            // Put the multipart in message
            message.setContent(multipart);
            Transport.send(message);
            MyLogger.log.info(String.format("Successfully sent invite with subject %s.", subject));

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void removeAllEmailsFromFolder(final String emailAddress, final String password,
                                                 TOnlineFolder foldername) throws MessagingException {
        removeOldEmailsFromFolder(emailAddress, password, foldername, 0);
    }

    public static void removeOldEmailsFromFolder(final String emailAddress, final String password,
                                                 TOnlineFolder foldername, long deltaInThePast_seconds) throws MessagingException {
        Session session = getSession(emailAddress, password);
        Date now = new Date();
        long maxMillis = now.getTime() - deltaInThePast_seconds * 1000;
        try {
            Store store = session.getStore("imaps");
            store.connect("secureimap.ver.sul.t-online.de", emailAddress, password);
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            MyLogger.log.info("message count in folder " + foldername.toString() + ":" + messages.length);
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                if ((message.getReceivedDate() != null) && (message.getReceivedDate().getTime() < maxMillis)) {
                    MyLogger.log.info("---------------------------------");
                    MyLogger.log.info("Email Number " + (i + 1));
                    MyLogger.log.info("Subject: " + message.getSubject());
                    MyLogger.log.info("From: " + message.getFrom()[0]);
                    MyLogger.log.info("Received: " + message.getReceivedDate().toString());
                    message.setFlag(Flags.Flag.DELETED, true);
                    MyLogger.log.info("Deleting message: " + message.getSubject());
                    MyLogger.log.info("---------------------------------");
                }
            }
            // expunges the folder to remove messages which are marked deleted
            folder.close(true);
            store.close();
        } catch (MessagingException e) {
            MyLogger.log.info("could not open folder " + foldername + ": " + e.getMessage());
            throw e;
        }
    }

    public static void copyAllEmailsFromFolder(final String sourceEmailAddress, final String sourcePassword,
                                               final String sourceFolder, final String destinationEmailAddress, final String destinationPassword,
                                               final String destinationFolder) throws MessagingException {
        Session session = getSession(sourceEmailAddress, sourcePassword);
        Store store = session.getStore("imaps");
        store.connect("secureimap.ver.sul.t-online.de", sourceEmailAddress, sourcePassword);
        // list folders
        javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
        for (javax.mail.Folder folder : folders) {
            System.out.println("Found folders in source, and number of emails; " + folder.getFullName() + ": "
                    + folder.getMessageCount());
        }
        Folder folderSource = store.getFolder(sourceFolder);
        try {
            folderSource.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            MyLogger.log.info("could not open folder " + sourceFolder + ": " + e.getMessage());
            throw e;
        }
        // retrieve the messages from the folder in an array and print it
        Message[] messages = folderSource.getMessages();
        MyLogger.log.info("message count in folder " + sourceFolder + ":" + messages.length);

        Session session2 = getSession(destinationEmailAddress, destinationPassword);
        Store store2 = session2.getStore("imaps");
        store2.connect("secureimap.ver.sul.t-online.de", destinationEmailAddress, destinationPassword);
        // list folders
        javax.mail.Folder[] folders2 = store2.getDefaultFolder().list("*");
        for (javax.mail.Folder folder : folders2) {
            System.out.println("Found folders in destination, and number of emails; " + folder.getFullName() + ": "
                    + folder.getMessageCount());
        }
        Folder folderDestination = store2.getFolder(destinationFolder);

        try {
            folderDestination.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            log.info("could not open destination folder " + destinationFolder + ": " + e.getMessage());
            throw e;
        }

        folderDestination.appendMessages(messages);
        System.out.println("After move in destination, number of emails: " + folderDestination.getFullName() + ": "
                + folderDestination.getMessageCount());
        folderSource.close(true);
        folderDestination.close(true);

        store.close();
        store2.close();
    }

    private static Session getSession(final String emailAddress, final String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "securesmtp.ver.sul.t-online.de");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", true);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, password);
            }
        });

        return session;
    }

    private static MimeMessage getMailMessage(Session session, final String fromEmailAddress, String toEmailAddress,
                                              String subject, String body, String attachmentPath) throws AddressException, MessagingException {

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(fromEmailAddress));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));

        // Set Subject: header field
        message.setSubject(subject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(body);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment, if any
        if (attachmentPath != null) {
            // extract filename using regex
            String filename = null;
            Pattern p = Pattern.compile(".*/(.*\\..*)");
            Matcher m = p.matcher(attachmentPath);
            if (m.find()) {
                filename = m.group(1);
                log.info("Attaching file to new email: " + filename);
            }
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        }

        // Send the complete message parts
        message.setContent(multipart);

        return message;
    }

    private static BodyPart buildCalendarPart(String fromEmailAddress) throws Exception {

        BodyPart calendarPart = new MimeBodyPart();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date start = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        Date end = cal.getTime();

        // check the icalendar spec in order to build a more complicated meeting
        // request
        String calendarContent = "BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n" + "PRODID: BCP - Meeting\n" + "VERSION:2.0\n"
                + "BEGIN:VEVENT\n" + "DTSTAMP:" + iCalendarDateFormat.format(start) + "\n" + "DTSTART:"
                + iCalendarDateFormat.format(start) + "\n" + "DTEND:" + iCalendarDateFormat.format(end) + "\n"
                + "SUMMARY:test request\n" + "UID:324\n"
                + "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:" + fromEmailAddress + "\n"
                + "ORGANIZER:MAILTO:" + fromEmailAddress + "\n" + "LOCATION:on the net\n"
                + "DESCRIPTION:learn some stuff\n" + "SEQUENCE:0\n" + "PRIORITY:5\n" + "CLASS:PUBLIC\n"
                + "STATUS:CONFIRMED\n" + "TRANSP:OPAQUE\n" + "BEGIN:VALARM\n" + "ACTION:DISPLAY\n"
                + "DESCRIPTION:REMINDER\n" + "TRIGGER;RELATED=START:-PT00H15M00S\n" + "END:VALARM\n" + "END:VEVENT\n"
                + "END:VCALENDAR";

        calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
        calendarPart.setContent(calendarContent, "text/calendar;method=CANCEL");

        return calendarPart;
    }

    // This method can read all the folders of an imap account for development
    public static void read() {
        Properties props = new Properties();
        try {
            props.put("mail.smtp.host", "securesmtp.ver.sul.t-online.de");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.debug", true);
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("secureimap.ver.sul.t-online.de", "vtu.test1@ver.sul.t-online.de", "1234test");

            javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
            for (javax.mail.Folder folder : folders) {
                if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                    log.info(folder.getFullName() + ": " + folder.getMessageCount());
                }
            }
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailWithAttachments(final String fromEmailAddress, final String password,
                                                String toEmailAddress, String subject, String body, List<String> attachmentPath)
            throws AddressException, MessagingException {

        Session session = getSession(fromEmailAddress, password);

        // Create a MimeMessage object.
        MimeMessage message = getMailMessage(session, fromEmailAddress, toEmailAddress, subject, body, attachmentPath);

        Transport.send(message);

        log.info(String.format("Successfully sent email with subject %s.", subject));
    }

    private static MimeMessage getMailMessage(Session session, final String fromEmailAddress, String toEmailAddress,
                                              String subject, String body, List<String> attachmentPath) throws AddressException, MessagingException {

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(fromEmailAddress));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));

        // Set Subject: header field
        message.setSubject(subject);

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(body);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment, if any
        for (String attachment : attachmentPath) {
            if (attachmentPath != null) {
                // extract filename using regex
                String filename = null;
                Pattern p = Pattern.compile(".*/(.*\\..*)");
                Matcher m = p.matcher(attachment);
                if (m.find()) {
                    filename = m.group(1);
                    log.info("Attaching file to new email: " + filename);
                }
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Send the complete message parts
        message.setContent(multipart);

        return message;
    }

    public static String sentEmailPastDate(final String fromEmailAddress, final String password, String toEmailAddress,
                                           String anEmailAddress, String subject, String body, TOnlineFolder foldername, int Days)
            throws AddressException, MessagingException {
        Session session = getSession(toEmailAddress, password);

        // Create a default MimeMessage object.
        MimeMessage message = getMailMessage(session, fromEmailAddress, toEmailAddress, subject, body, attachMentPath);

        message.setSentDate(getMeDay(Days));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(anEmailAddress));

        try {
            Store store = session.getStore("imaps");
            store.connect("secureimap.ver.sul.t-online.de", toEmailAddress, password);
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            store.close();

            log.info(String.format("Successfully put email with subject %s in " + foldername + ": ", subject));

        } catch (MessagingException e) {
            log.info("could not open folder " + foldername + ": " + e.getMessage());
            throw e;
        }

        return subject;

    }

    public static String sentEmailWithMultipleRecipient(final String fromEmailAddress, final String password,
                                                        String toEmailAddress, String anEmailAddress, String subject, String body, TOnlineFolder foldername)
            throws AddressException, MessagingException {
        Session session = getSession(toEmailAddress, password);

        // Create a default MimeMessage object.
        MimeMessage message = getMailMessage(session, fromEmailAddress, toEmailAddress, subject, body, attachMentPath);

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(anEmailAddress));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(toEmailAddress));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(anEmailAddress));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(toEmailAddress));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(anEmailAddress));

        try {
            Store store = session.getStore("imaps");
            store.connect("secureimap.ver.sul.t-online.de", toEmailAddress, password);
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            store.close();

            log.info(String.format("Successfully put email with subject %s in " + foldername + ": ", subject));

        } catch (MessagingException e) {
            log.info("could not open folder " + foldername + ": " + e.getMessage());
            throw e;
        }

        return subject;

    }

    public static void deleteEmailWithSubject(final String fromEmailAddress, final String password,
                                              String toEmailAddress, String subject, TOnlineFolder foldername) throws MessagingException

    {
        Session session = getSession(toEmailAddress, password);

        try {

            Store store = session.getStore("imaps");

            store.connect("secureimap.ver.sul.t-online.de", toEmailAddress, password);

            Folder[] f = store.getDefaultFolder().list();
            for (Folder folder : f) {

                System.out.println(folder.getName());
            }
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);

            Message[] msg = folder.getMessages();

            for (int i = 0; i < msg.length; i++) {
                if (msg[i].getSubject().equalsIgnoreCase(subject)) {
                    msg[i].setFlag(Flags.Flag.DELETED, true);
                    break;

                }
            }

            folder.close(true);
            store.close();

        } catch (MessagingException e) {
            throw e;
        }
    }

    private static Date getMeDay(int Day) {
        return new Date(System.currentTimeMillis() - 24 * Day * 60 * 60 * 1000);
    }

    public static Date getReceivedTimeEmailWithSubject(final String fromEmailAddress, final String password,
                                                       String toEmailAddress, String subject, TOnlineFolder foldername) throws MessagingException

    {
        Session session = getSession(toEmailAddress, password);
        Date sentDate = new Date();

        try {

            Store store = session.getStore("imaps");

            store.connect("secureimap.ver.sul.t-online.de", toEmailAddress, password);

            Folder[] f = store.getDefaultFolder().list();
            for (Folder folder : f) {

                System.out.println(folder.getName());
            }
            Folder folder = store.getFolder(foldername.toString());
            folder.open(Folder.READ_WRITE);

            Message[] msg = folder.getMessages();

            for (int i = 0; i < msg.length; i++) {
                if (msg[i].getSubject().equalsIgnoreCase(subject)) {
                    sentDate = msg[i].getReceivedDate();
                    break;

                }
            }

            folder.close(true);
            store.close();
            return sentDate;

        } catch (MessagingException e) {
            throw e;
        }
    }

    // Declaration of standard T-Online folders
    public enum TOnlineFolder {
        INBOX, DRAFT, SENT, TRASH, SPAM;

        private final static String INBOX_VALUE = "INBOX";
        private final static String DRAFT_VALUE = "INBOX.Drafts";
        private final static String SENT_VALUE = "INBOX.Sent";
        private final static String TRASH_VALUE = "INBOX.Trash";
        private final static String SPAM_VALUE = "INBOX.Spam";

        @Override
        public String toString() {
            switch (this) {
                case INBOX:
                    return INBOX_VALUE;
                case DRAFT:
                    return DRAFT_VALUE;
                case SENT:
                    return SENT_VALUE;
                case TRASH:
                    return TRASH_VALUE;
                case SPAM:
                    return SPAM_VALUE;
                default:
                    return super.toString();
            }
        }
    }
}
