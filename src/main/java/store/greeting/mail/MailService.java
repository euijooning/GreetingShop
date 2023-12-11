package store.greeting.mail;

//@Service
//@RequiredArgsConstructor
//public class MailService {
//
////    private final JavaMailSender emailSender; // MailConfig에서 빈으로 정의해놓은 객체
////    private final MemberRepository memberRepository;
////
////    public final String ePw = createKey();
////
////    private MimeMessage createMessage(String to) throws Exception {
////        System.out.println("보내는 대상 : " + to);
////        System.out.println("인증 번호 : " + ePw);
////        MimeMessage message = emailSender.createMimeMessage();
////
////        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
////        message.setSubject("그리팅스토어 회원가입 이메일 인증"); //제목
////
////        String msgg = "";
////        msgg += "<div style='margin:20px;'>";
////        msgg += "<h1> 안녕하세요 그리팅스토어입니다. </h1>";
////        msgg += "<br>";
////        msgg += "<p>아래 코드를 복사해 입력해주세요.<p>";
////        msgg += "<br>";
////        msgg += "<p>고맙습니다.</p>";
////        msgg += "<br>";
////        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
////        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
////        msgg += "<div style='font-size:130%'>";
////        msgg += "CODE: <strong>";
////        msgg += ePw + "</strong><div><br/>";
////        msgg += "</div>";
////        message.setText(msgg, "utf-8", "html"); //내용
////        message.setFrom(new InternetAddress("eui4453@naver.com", "그리팅스토어")); //properties에 입력한 이메일
////
////        return message;
////
////    }
////
////
////    public static String createKey() {
////        StringBuffer key = new StringBuffer();
////        Random rnd = new Random();
////
////        for (int i = 0; i < 8; i++) {
////            int index = rnd.nextInt(3);
////
////            switch (index) {
////                case 0:
////                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
////                    // a~z (ex. 1+97=98 => (char)98 = 'b')
////                    break;
////                case 1:
////                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
////                    //A~Z
////                case 2:
////                    key.append((rnd.nextInt(10)));
////                    //0~9
////                    break;
////            }
////        }
////        return key.toString();
////    }
////
////    public String sendSimpleMessage(String to) throws Exception {
////        MimeMessage message = createMessage(to);
////        try {
////            emailSender.send(message);
////        } catch (MailException es) {
////            throw new IllegalArgumentException();
////        }
////        return ePw;
////    }
////
////
////    // 임시 비밀번호 생성
////    public static String makeTempPassword(){
////        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
////                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
////
////        String str = "";
////
////        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
////        int index = 0;
////        for (int i = 0; i < 10; i++) {
////            index = (int) (charSet.length * Math.random());
////            str += charSet[index];
////        }
////        return str;
////    }
////
//////    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
//////    public MailDto createMailContentAndChangePassword(String email) {
//////
//////        Principal principal = PrincipalContext.getPrincipal(); // Principal 가져오기
//////        String[] userInfo = AuthTokenParser.getParseToken(principal);
//////        userInfo[0] = email;
//////
//////        String str = makeTempPassword();
//////        MailDto dto = new MailDto();
//////
//////        dto.setAddress(userInfo[0]);
//////        dto.setTitle("[그리팅스토어 임시 비밀번호 안내].");
//////        dto.setMessage("안녕하세요. 그리팅스토어 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
//////                + str + " 입니다." + "로그인 후에 비밀번호를 변경해주세요!");
//////
//////        updateTemporaryPassword(str, userInfo[0]);
//////        return dto;
//////    }
////
////    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
////    public MailDto createMailContentAndChangePassword(String email) {
////        String str = makeTempPassword();
////        MailDto dto = new MailDto();
////        dto.setAddress(email);
////        dto.setTitle("[그리팅스토어 임시 비밀번호 안내].");
////        dto.setMessage("안녕하세요. 그리팅스토어 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
////                + str + " 입니다." + "로그인 후에 비밀번호를 변경해주세요!");
////        updatePassword(str, email);
////        return dto;
////    }
////
////    // MailDto를 바탕으로 실제 이메일 전송
////    public void mailSend(MailDto mailDto) {
////        System.out.println("전송 완료!");
////        SimpleMailMessage message = new SimpleMailMessage();
////        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
////        message.setTo(mailDto.getAddress());
////        message.setSubject(mailDto.getTitle());
////        message.setText(mailDto.getMessage());
////        message.setFrom("eui4453@naver.com");
////        message.setReplyTo("eui4453@naver.com");
////        System.out.println("message : " + message);
////        javaMailSender.send(message);
////    }
////
//////    //임시 비밀번호로 업데이트
//////    public boolean updateTemporaryPassword(String str, String email) {
//////
//////        Principal principal = PrincipalContext.getPrincipal(); // Principal 가져오기
//////        String[] userInfo = AuthTokenParser.getParseToken(principal);
//////        userInfo[0] = email; // 회원정보를 받아온 이메일로 확실하게 심어주기 위해.
//////
//////        try {
//////            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//////            String encodedPassword = encoder.encode(str); // 패스워드 암호화
//////            Member member = memberRepository.findByEmail(userInfo[0]); // 여기서는 로그인 타입은 굳이 필요없을듯 함.
//////            member.updatePassword(encodedPassword);
//////            memberRepository.save(member);
//////            return true;
//////        } catch (Exception e) {
//////            return false;
//////        }
//////    }
////
////    //임시 비밀번호로 업데이트
////    public boolean updatePassword(String str, String email){
////        try {
////            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
////            String encodePw = encoder.encode(str); // 패스워드 암호화
////            Member member = memberRepository.findByEmail(email);
////            member.updatePassword(encodePw);
////            memberRepository.save(member);
////            return true;
////        } catch (Exception e) {
////            return false;
////        }
////    }
//
//
//
//}

