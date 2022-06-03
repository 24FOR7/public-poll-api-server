package twentyfourFORseven.publicpoll.Filters;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.bind.annotation.ResponseBody;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;

@Component
public class TokenFilter implements Filter{

    private static final String[] whiteList = {"/"};    //필터 하지 않을 주소 저장 ex)로그인, 회원가입

    @Override
    @ResponseBody
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        if(isNotWhiteList(httpReq.getRequestURI())){
            try{
                String idToken = httpReq.getHeader("Authorization");    //헤더의 Authorization에서 토큰 추출
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken); //토큰 검증, 디코드
                httpReq.setAttribute("uid", decodedToken.getUid()); //req에 토큰 넣기
            } catch (FirebaseAuthException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getAuthErrorCode());

                httpRes.sendError(401, "verofy token fail");    //검증 실패시 에러 반환
                return ;
            }
        }
        chain.doFilter(httpReq, httpRes);
    }

    /**
     * 화이트리스트 체크
     * 화이트리스트 O-> return false
     * 화이트리스트 X-> return true
     * =============================================
     * 모든 api를 테스트 해야 해서 강제로 false반환하게 해뒀음
     * 필터 테스트 하려면 주석 처리 해둔 부분 사용
     */
    private boolean isNotWhiteList(String requestURI){
        //return false;
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
