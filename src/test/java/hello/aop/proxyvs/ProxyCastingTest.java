package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class ProxyCastingTest {

    /**
     * JDK 동적 프록시는 대상 객체인 MemberServiceImpl로 캐스팅 할 수 없다.
     * CGLIB 프록시는 대상 객체인 MemberServiceImpl로 캐스팅 할 수 있다.
     */

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시

        // JDK 동적 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 발생
//        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // CGLIB 프록시

        // CGLIB 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }

}
