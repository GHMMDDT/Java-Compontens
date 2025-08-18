import com.java.components.lang.CharArrayBuilder;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		CharArrayBuilder sb = new CharArrayBuilder(
			new char[] { 'H', 'e', 'l', 'l', 'o', '!' }, 4
		);
		sb.setOnAppendedChangedListener(new CharArrayBuilder.OnAppendedChangedListener() {
			@Override
			public void onAppendChanged(char[] oldChar, char[] newChar, char[] insert) {
				System.out.println(Arrays.toString(oldChar) + ": old");
				System.out.println(Arrays.toString(newChar) + ": new");
				System.out.println(Arrays.toString(insert) + ": insert");
			}
		});
		sb.append(" in Java Components is? ")
				.append(true)
				.append('!')
				.append('\n');
		System.out.println();
		System.out.println(sb);
		System.out.println(sb.getLength());
		System.out.println(sb.getSize());
		System.out.println(sb.getMissingCapacity());
		/* Output:
[o, !]: old
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  ]: new
[ , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  ]: insert
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  ]: old
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  , t, r, u, e,  ,  ,  ,  ,  ,  ,  ,  ,  ]: new
[t, r, u, e]: insert
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  , t, r, u, e, !,  ,  ,  ,  ,  ,  ,  ,  ]: old
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  , t, r, u, e, !,  ,  ,  ,  ,  ,  ,  ,  ]: new
[!]: insert
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  , t, r, u, e, !,
,  ,  ,  ,  ,  ,  ,  ]: old
[o, !,  , i, n,  , J, a, v, a,  , C, o, m, p, o, n, e, n, t, s,  , i, s, ?,  , t, r, u, e, !,
,  ,  ,  ,  ,  ,  ,  ]: new
[
]: insert

o! in Java Components is? true!

39
32
7
		 */
	}
}