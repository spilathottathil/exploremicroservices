package com.sarath.work.explorems;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


class ExploremsApplicationTests {

	@Test
	void contextLoads() {
		String S = "A", T = "A";

		System.out.println(minWindow(S,T));

	}

	private static String minWindow(String s, String t) {

		//sliding window
		int l=0,r=0,minLeft=0;
		Map<Character,Integer> lookUp = new HashMap<>();

		//count of chars
		for(char c : t.toCharArray()){
			if(lookUp.containsKey(c)){
				lookUp.put(c,lookUp.get(c)+1);
			}else{
				lookUp.put(c,1);
			}
		}
		int req =t.length();
		int minLen = Integer.MAX_VALUE, match=0;

		while( r< s.length()){

			if(lookUp.containsKey(s.charAt(r))){
				lookUp.put(s.charAt(r),lookUp.get(s.charAt(r))-1);
				if(lookUp.get(s.charAt(r)) >=0 ) match++;

				while (match == req && l <= r ){ //now we got a match
					if(r - l +1 < minLen) { //only if its s less than the current minlength
						minLen = r - l + 1;
						minLeft = l;
					}
					//now shrink the pointer
					if(lookUp.containsKey(s.charAt(l))){
						lookUp.put(s.charAt(l),lookUp.get(s.charAt(l))+1);
						if(lookUp.get(s.charAt(l)) >0 ) match--;
					}
					l++;
				}
			}
			r++;
		}
		System.out.println(minLeft + " minLeft");
		System.out.println(minLen + " minLen");
		if(minLen > s.length() ) return  "";
		return s.substring(minLeft,minLeft+minLen);
	}

}
