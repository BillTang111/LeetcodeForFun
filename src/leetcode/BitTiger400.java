package leetcode;

public class BitTiger400 {

	
	
	//482. License Key Formatting (47.11%)
    public String licenseKeyFormatting(String S, int K) {
        if(S.length()==0 || K==0) return S;
        StringBuilder sb = new StringBuilder();
        int term=0;
        for(int i=S.length()-1;i>=0;i--){
        	if(S.charAt(i)=='-') continue;
            sb.append(S.charAt(i));
        	if(++term % K == 0) sb.append('-');
        }
        //remove '-' at the end of string
        int pt=sb.length()-1;
        while(pt>=0 && sb.charAt(pt)=='-') {

        	sb.deleteCharAt(pt);
            pt--;
        }
		return sb.reverse().toString().toUpperCase();
    }
}
