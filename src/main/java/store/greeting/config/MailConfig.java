package store.greeting.config;

//@Configuration
//public class MailConfig {
//
////    @Value("${naverId}")
////    private String naverId;
////
////    @Value("${naverPasswd}")
////    private String naverPasswd;
////
////    @Bean
////    public JavaMailSender javaMailService(){
////        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
////        javaMailSender.setHost("smtp.naver.com");
////        javaMailSender.setUsername(naverId);
////        javaMailSender.setPassword(naverPasswd);
////        javaMailSender.setPort(465);
////        javaMailSender.setProtocol("smtps"); // SSL 사용
////        javaMailSender.setJavaMailProperties(getMailProperties());
////
////        return javaMailSender;
////    }
////
////    private Properties getMailProperties() {
////        Properties properties = new Properties();
////        properties.setProperty("mail.smtp.auth", "true");
////        properties.setProperty("mail.smtp.starttls.enable", "true");
////        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com");
////        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
////        properties.setProperty("mail.smtp.socketFactory.port", "465");
////
////        return properties;
////    }
////
////
//////    private Properties getMailProperties() {
//////        Properties properties = new Properties();
//////        properties.setProperty("mail.transport.protocol", "smtp");
//////        properties.setProperty("mail.smtp.auth", "true");
//////        properties.setProperty("mail.smtp.starttls.enable","true");
//////        properties.setProperty("mail.debug","true");
//////        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com");
//////        properties.setProperty("mail.smtp.ssl.enable","true");
//////        return properties;
//////    }
//}