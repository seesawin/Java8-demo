package dataStructure;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("================== Map ======================");
        System.out.println("=============================================");
        /**
         * Map介面
         * 以關鍵值（key）儲存
         * 關鍵值對應到的資料，即對應值（value）
         *
         * Map 是一種把鍵物件和值物件進行關聯的容器，而一個值物件又可以是一個Map，依次類推，
         * 這樣就可形成一個多級對映。對於鍵物件來說，像Set一樣，一個 Map容器中的鍵物件不允許重複，
         * 這是為了保持查詢結果的一致性;如果有兩個鍵物件一樣，那你想得到那個鍵物件所對應的值物件時就有問題了，
         * 可能你得到的並不是你想的那個值物件，結果會造成混亂，所以鍵的唯一性很重要，也是符合集合的性質的。
         * 當然在使用過程中，某個鍵所對應的值物件可能會發生變化，這時會按照最後一次修改的值物件與鍵對應。
         * 對於值物件則沒有唯一性的要求，你可以將任意多個鍵都對映到一個值物件上，
         * 這不會發生任何問題（不過對你的使用卻可能會造成不便，你不知道你得到的到底是那一個鍵所對應的值物件）。
         *
         * */
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("a3", "cc");
        hashMap.put("a1", "aa");
        hashMap.put("a5", "ee");
        hashMap.put("a2", "bb");
        hashMap.put("a4", "dd");
        hashMap.put("a4", "dd");
        hashMap.forEach((k, v) -> {
            System.out.println("HashMap > " + k + ":" + v);
        });
        System.out.println("---------------------------------------------");
        Hashtable<String, Object> hashtable = new Hashtable();
        hashtable.put("a3", "cc");
        hashtable.put("a1", "aa");
        hashtable.put("a5", "ee");
        hashtable.put("a2", "bb");
        hashtable.put("a4", "dd");
        hashtable.put("a4", "dd");
        hashtable.forEach((k, v) -> {
            System.out.println("Hashtable > " + k + ":" + v);
        });
        System.out.println("---------------------------------------------");
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("a3", "cc");
        linkedHashMap.put("a1", "aa");
        linkedHashMap.put("a5", "ee");
        linkedHashMap.put("a2", "bb");
        linkedHashMap.put("a4", "dd");
        linkedHashMap.put("a4", "dd");
        linkedHashMap.forEach((k, v) -> {
            System.out.println("LinkedHashMap > " + k + ":" + v);
        });
        System.out.println("---------------------------------------------");
        /**
         * 元素會依關鍵值由小至大排序
         *
         * */
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("a3", "cc");
        treeMap.put("a1", "aa");
        treeMap.put("a5", "ee");
        treeMap.put("a2", "bb");
        treeMap.put("a4", "dd");
        treeMap.put("a4", "dd");
        treeMap.forEach((k, v) -> {
            System.out.println("TreeMap > " + k + ":" + v);
        });
        System.out.println("=============================================");
        System.out.println("=============== Collection ==================");
        System.out.println("=============================================");
        /**
         *List介面
         * 屬於有序集合物件（ordered collection）
         * 元素可以重複
         * 元素具有索引值（index） 
         * List介面和SortedSet介面的比較
         * List會依照索引值來排列元素的位置
         * SortedSet會根據元素本身的大小來排列
         *
         * List是有序的Collection，使用此介面能夠精確的控制每個元素插入的位置。
         * 使用者能夠使用索引（元素在List中的位置，類似於陣列下標）來訪問List中的元素，這類似於Java的陣列。
         *
         * 元素加入ArrayList物件時，是用索引值（index）儲存
         *
         * 同Vector一樣是一個基於陣列上的連結串列，但是不同的是ArrayList不是同步的。
         * 所以在效能上要比Vector好一些，但是當執行到多執行緒環境中時，可需要自己在管理執行緒的同步問題。
         *
         * SUM
         * 所有的List中只能容納單個不同型別的物件組成的表，而不是Key－Value鍵值對。例如：[ tom,1,c ]
         * 所有的List中可以有相同的元素，例如Vector中可以有 [ tom,koo,too,koo ]
         * 所有的List中可以有null元素，例如[ tom,null,1 ]
         *
         *      基於Array的List（Vector，ArrayList）適合查詢，而LinkedList 適合新增，刪除操作
         * */
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(4);
        arrayList.add(3);
        arrayList.add(5);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(2);
        System.out.println("ArrayList...");
        arrayList.forEach(System.out::println);
        System.out.println("----------------------------------------------");
        /**
         * 基於陣列（Array）的List，其實就是封裝了陣列所不具備的一些功能方便我們使用，所以它難易避免陣列的限制，
         * 同時效能也不可能超越陣列。所以，在可能的情況下，我們要多運用陣列。另外很重要的一點就是Vector是執行緒同步的(sychronized)的，
         * 這也是Vector和ArrayList 的一個的重要區別。
         *
         * */
        Vector<Integer> vector = new Vector<>();
        vector.add(4);
        vector.add(3);
        vector.add(5);
        vector.add(1);
        vector.add(2);
        vector.add(2);
        System.out.println("Vector...");
        vector.forEach(System.out::println);
        System.out.println("----------------------------------------------");
        Stack<Integer> stack = new Stack<>();
        stack.add(4);
        stack.add(3);
        stack.add(5);
        stack.add(1);
        stack.add(2);
        stack.add(2);
        System.out.println("Stack...");
        stack.forEach(System.out::println);
        System.out.println("----------------------------------------------");
        /**
         * 鏈結串列（linked list）
         * LinkedList不同於前面兩種List，它不是基於陣列的，所以不受陣列效能的限制。
         * 它每一個節點（Node）都包含兩方面的內容：
         * 1.節點本身的資料（data）；
         * 2.下一個節點的資訊（nextNode）。
         * 所以當對LinkedList做新增，刪除動作的時候就不用像基於陣列的ArrayList一樣，必須進行大量的資料移動。只要更改nextNode的相關資訊就可以實現了，這是LinkedList的優勢。
         * */
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(4);
        linkedList.add(3);
        linkedList.add(5);
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(2);
//        linkedList.
        System.out.println("LinkedList...");
        linkedList.forEach(System.out::println);
        System.out.println("-----------------------------------------------");
        /**
         * HashSet類別
         * 實作Set介面的類別
         * 利用雜湊表（hash table）演算法改進執行的效率
         * 儲存元素時，元素排列的順序和原先加入時的順序可能不同
         * HashSet物件裡的元素都是唯一存在的
         *
         * 雖然Set同List都實現了Collection介面，但是他們的實現方式卻大不一樣。List基本上都是以Array為基礎。
         * 但是Set則是在 HashMap的基礎上來實現的，這個就是Set和List的根本區別。HashSet的儲存方式是把HashMap中的Key作為Set的對應儲存項。
         * 看看 HashSet的add（Object obj）方法的實現就可以一目瞭然了。
         * */
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(4);
        hashSet.add(3);
        hashSet.add(5);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(2);
        System.out.println("HashSet...");
        hashSet.forEach(System.out::println);
        System.out.println("-----------------------------------------------");
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(4);
        linkedHashSet.add(3);
        linkedHashSet.add(5);
        linkedHashSet.add(1);
        linkedHashSet.add(2);
        linkedHashSet.add(2);
        System.out.println("LinkedHashSet...");
        linkedHashSet.forEach(System.out::println);
        System.out.println("-----------------------------------------------");
        /**
         * SortedSet介面
         * 資料會由小而大排列
         * 為一種排序集合物件（sorted collection）
         *
         * 有序的Set，通過SortedMap來實現的。
         *
         * SUM
         * （1）Set實現的基礎是Map（HashMap）
         * （2）Set中的元素是不能重複的，如果使用add(Object obj)方法新增已經存在的物件，則會覆蓋前面的物件
         *
         * */
        SortedSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(4);
        treeSet.add(3);
        treeSet.add(5);
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(2);
        System.out.println("TreeSet...");
        treeSet.forEach(System.out::println);

        /*
        ————說明———–

        一、幾個常用類的區別
            1．ArrayList: 元素單個，效率高，多用於查詢
            2．Vector: 元素單個，執行緒安全，多用於查詢
            3．LinkedList:元素單個，多用於插入和刪除
            4．HashMap: 元素成對，元素可為空
            5．HashTable: 元素成對，執行緒安全，元素不可為空
        二、Vector、ArrayList和LinkedList
        大多數情況下，從效能上來說ArrayList最好，但是當集合內的元素需要頻繁插入、刪除時LinkedList會有比較好的表現，
        但是它們三個效能都比不上陣列，另外Vector是執行緒同步的。所以：
            如果能用陣列的時候(元素型別固定，陣列長度固定)，請儘量使用陣列來代替List；
            如果沒有頻繁的刪除插入操作，又不用考慮多執行緒問題，優先選擇ArrayList；
            如果在多執行緒條件下使用，可以考慮Vector；
            如果需要頻繁地刪除插入，LinkedList就有了用武之地；
            如果你什麼都不知道，用ArrayList沒錯。
        三、Collections和Arrays
        在 Java集合類框架裡有兩個類叫做Collections（注意，不是Collection！）和Arrays，這是JCF裡面功能強大的工具，但初學者往往會忽視。按JCF文件的說法，這兩個類提供了封裝器實現（Wrapper Implementations）、資料結構演算法和陣列相關的應用。
        想必大家不會忘記上面談到的“折半查詢”、“排序”等經典演算法吧，Collections類提供了豐富的靜態方法幫助我們輕鬆完成這些在資料結構課上煩人的工作：
        binarySearch：折半查詢。
        sort：排序，這裡是一種類似於快速排序的方法，效率仍然是O(n * log n)，但卻是一種穩定的排序方法。
        reverse：將線性表進行逆序操作，這個可是從前資料結構的經典考題哦！
        rotate：以某個元素為軸心將線性表“旋轉”。
        swap：交換一個線性表中兩個元素的位置。
        ……
        Collections還有一個重要功能就是“封裝器”（Wrapper），它提供了一些方法可以把一個集合轉換成一個特殊的集合，如下：
        unmodifiableXXX：轉換成只讀集合，這裡XXX代表六種基本集合介面：Collection、List、Map、Set、SortedMap和SortedSet。如果你對只讀集合進行插入刪除操作，將會丟擲UnsupportedOperationException異常。
        synchronizedXXX：轉換成同步集合。
        singleton：建立一個僅有一個元素的集合，這裡singleton生成的是單元素Set，
        singletonList和singletonMap分別生成單元素的List和Map。
        空集：由Collections的靜態屬性EMPTY_SET、EMPTY_LIST和EMPTY_MAP表示。


        1.Collction： 
        Collection介面繼承自超級介面Iterator,是Collection層次結構中的根介面。Collection表示一組物件，這些物件也被稱為Collection的元素。
        一些Collection允許有重複的元素（例如List），但是另一些則不允許有重複的元素，即可為無序的（如Set）。
        JDK不提供此介面的任何直接實現---它會提供更為具體的子介面(如Set和List),這從上面的UML也可以看出來。此介面用來傳遞Collection,
        並在需要最大普遍性的地方操作這些Collection。其實現類的底層是由陣列或者連結串列組成，陣列是通過首地址＋（元素長度＊下標），
        即通過下標查詢的，因此查詢速度快，而增刪慢（在增刪的時候，陣列需要整體的移動，所以慢）；連結串列不維護序號，即連結串列不存在下標的概念，
        **所以查詢很慢（通過地址查詢的），而增刪快（直接通過地址刪掉某一個元素，其它元素不需要移動）   陣列：查詢快，增刪慢；連結串列：查詢慢，增刪快

        1.1.List：有序，可重複
          ArrayList ：底層是陣列結構，執行緒不安全。查詢快，增刪慢
          LinkedList ：底層是連結串列結構，執行緒不安全。查詢慢，增刪快
          Vector：底層是陣列結構，是執行緒安全的，所以效率很低，已經被ArrayList取代
S        1.2.Set ：無序，不可重複
          HashSet類 及其實現類LinkedHashSet：底層是使用了雜湊表來支援的，特點： 存取速度快，執行緒不安全，集合元素允許為NULL
          SortedSet介面及其實現類TreeSet：如果元素具備自然順序 的特性，那麼就按照元素自然順序的特性進行排序儲存。
        1.3.EnumSet
          EnumSet類是專為列舉類設計的集合類，EnumSet中的所有元素都必須是指定列舉型別的列舉值
        2.Map
        Map用於儲存具有對映關係的資料，因此Map集合裡儲存著兩組值，一組值用於儲存Map裡的key，
        另外一組用於儲存Map裡的value，key和value都是可以任意引用型別的資料。Map的key不允許重複，
        即同一個Map物件的任何兩個key通過equals方法比較總是返回false. 給key-value起個名字：Entry，表示一個鍵值對，
        對應Map的一個實體;把Entry放到集合set中就是一個Map 如果把Map所有value放在一起來看，元素與元素之間可以重複，
        每個元素可以根據索引來查詢，相當於list集合，只是Map中的索引不再使用整數值，而是以另外一個物件作為索引。如果需要從List集合中取出元素，
        需要提供該元素的數字索引。 如果需要從Map中取出元素，需要提供該元素的key索引，因此，Map也被稱為字典。
        常見的實現類：
        2.1.HashMap:
          採用雜湊表演算法, 此時Map中的key不會保證新增的先後順序,key也不允許重複.key判斷重複的標準是: key1和key2是否equals為true,並且與hashCode相等.
        其中實現類LinkedHashMap採用了連結串列和雜湊表演算法
        2.2.TreeMap:
          sortedMap介面的實現類，採用紅黑樹演算法,此時Map中的key會按照自然順序或定製排序進行排序,
        key也不允許重複.key判斷重複的標準是: compareTo/compare的返回值是否為0.
        2.3.Hashtable:
            採用雜湊表演算法,是HashMap的前身(類似於Vector是ArrayList的前身).打死不用. 在Java的集合框架之前,
            表示對映關係就使用Hashtable.所有的方法都使用synchronized修飾符,執行緒安全的,但是效能相對HashMap較低.其子類Properties要求key和value都是String型別.
        * */
    }
}
