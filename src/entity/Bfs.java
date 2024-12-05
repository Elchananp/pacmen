
package entity;


import main.GamePanel;
import tile.TileManager;
import java.util.*;

public class Bfs {
    GamePanel gamePanel;
    TileManager tileManager;



    // גודל הלוח
    public int ROWS;
    public int COLS;
    // תור לבדיקת תאים (BFS)
    public Queue<String[]> queue;

    public Map<String, String> cameFrom;
    Stack< int[] > pathStack;




    // תנועות אפשריות (למעלה, למטה, שמאלה, ימינה)
    static final int[][] MOVES = {
            {-1, 0}, // למעלה
            {1, 0},  // למטה
            {0, -1}, // שמאלה
            {0, 1}   // ימינה
    };
    public Bfs(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.ROWS = gamePanel.maxScreenRow;
        this.COLS = gamePanel.maxScreenCol;
        this.queue = new LinkedList<>();
        this.cameFrom = new HashMap<>();
        this.pathStack = new Stack<>();
    }






    public void findShortestPath(int[][] grid, int[] start, int[] end, boolean flag ) {
        System.out.println("start: " + Arrays.toString(start));
        System.out.println("end: " + Arrays.toString(end));
        // איפוס מבנים לכל הרצה חדשה
        queue.clear();
        cameFrom.clear();

        // המרת נקודת ההתחלה למחרוזת
        String startStr = arrayToString(start);
        System.out.println("startStr: " + startStr);

        // הוספת נקודת ההתחלה לתור עם אבא null
        // בפעם הראשונה נכנס לדוגמא "0,0"
        queue.add(new String[]{startStr, null});

        while (!queue.isEmpty()) {
            // הוצאת התא הראשון מהתור
            String[] current = queue.poll();
            String child = current[0];
            String parent = current[1];

            // שמירת הקשר בין הבן לאבא
            cameFrom.put(child, parent);

            // אם הגענו לנקודת הסיום, יציאה מהלולאה
            if (child.equals(arrayToString(end))) {
                System.out.println("Found path!");
                reconstructPath(child);
                return;
            }



            // המרת הקואורדינטות בחזרה למערך int
            int[] currentCoords = stringToArray(child);

            // בדיקת תאים שכנים
            for (int[] move : MOVES) {
                int newRow = currentCoords[0] + move[0];
                int newCol = currentCoords[1] + move[1];

                // בדיקת גבולות הלוח ותאים חסומים
                if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS &&  !tileManager.tile[grid[newRow][newCol]].isBlocked && (!flag || !tileManager.tile[grid[newRow][newCol]].isBlockedForPinky)) {
                    String neighbor = arrayToString(new int[]{newRow, newCol});
                    if (!cameFrom.containsKey(neighbor)) {
                        queue.add(new String[]{neighbor, child});
                    }
                }
            }
        }

        System.out.println("No path found.");
    }



    public void findShortestPath(int[][] grid, int[] start, int[] end) {
        findShortestPath(grid, start, end, false);
    }





    public String arrayToString(int[] coordinates) {
        return coordinates[0] + "," + coordinates[1];
    }

    public int[] stringToArray(String str) {
        String[] parts = str.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }
    public void reconstructPath(String endStr) {
//         this.pathStack = new Stack<>();
//        pathStack.clear();

        String current = endStr;

        while (current != null) {
            pathStack.push(stringToArray(current));
            current = cameFrom.get(current);
        }
        pathStack.pop();// הוצאת הנתיב ההתחלתי (test)

    }

    public void printMap(int[][] grid) {
        System.out.println("{");
        for (int i = 0; i < grid.length; i++) {
            System.out.print("{");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
                if (j < grid[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}" + (i < grid.length - 1 ? "," : ""));
        }
        System.out.println("}");
    }
}





//
//package entity;
//
//
//import main.GamePanel;
//import tile.TileManager;
//import java.util.*;
//
//public class Bfs {
//    GamePanel gamePanel;
//    TileManager tileManager;
//
//
//
//    // גודל הלוח
//    public int ROWS;
//     public int COLS;
//    // תור לבדיקת תאים (BFS)
//    public Queue<String[]> queue;
//
//    public Map<String, String> cameFrom;
//    Stack< int[] > pathStack;
//
//
//
//
//    // תנועות אפשריות (למעלה, למטה, שמאלה, ימינה)
//    static final int[][] MOVES = {
//            {-1, 0}, // למעלה
//            {1, 0},  // למטה
//            {0, -1}, // שמאלה
//            {0, 1}   // ימינה
//    };
//    public Bfs(GamePanel gamePanel, TileManager tileManager) {
//        this.gamePanel = gamePanel;
//        this.tileManager = tileManager;
//        this.ROWS = gamePanel.maxScreenRow;
//        this.COLS = gamePanel.maxScreenCol;
//        this.queue = new LinkedList<>();
//        this.cameFrom = new HashMap<>();
//        this.pathStack = new Stack<>();
//    }
//
//
//
//
//
//
//    public void findShortestPath(int[][] grid, int[] start, int[] end ) {
//        System.out.println("start: " + Arrays.toString(start));
//        System.out.println("end: " + Arrays.toString(end));
//        // איפוס מבנים לכל הרצה חדשה
//        queue.clear();
//        cameFrom.clear();
//
//        // המרת נקודת ההתחלה למחרוזת
//        String startStr = arrayToString(start);
//        System.out.println("startStr: " + startStr);
//
//        // הוספת נקודת ההתחלה לתור עם אבא null
//        // בפעם הראשונה נכנס לדוגמא "0,0"
//        queue.add(new String[]{startStr, null});
//
//        while (!queue.isEmpty()) {
//            // הוצאת התא הראשון מהתור
//            String[] current = queue.poll();
//            String child = current[0];
//            String parent = current[1];
//
//            // שמירת הקשר בין הבן לאבא
//            cameFrom.put(child, parent);
//
//            // אם הגענו לנקודת הסיום, יציאה מהלולאה
//            if (child.equals(arrayToString(end))) {
//                System.out.println("Found path!");
//                reconstructPath(child);
//                return;
//            }
//
//
//
//            // המרת הקואורדינטות בחזרה למערך int
//            int[] currentCoords = stringToArray(child);
//
//            // בדיקת תאים שכנים
//            for (int[] move : MOVES) {
//                int newRow = currentCoords[0] + move[0];
//                int newCol = currentCoords[1] + move[1];
//
//                // בדיקת גבולות הלוח ותאים חסומים
//                if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS &&  !tileManager.tile[grid[newRow][newCol]].isBlocked) {
//                    String neighbor = arrayToString(new int[]{newRow, newCol});
//                    if (!cameFrom.containsKey(neighbor)) {
//                        queue.add(new String[]{neighbor, child});
//                    }
//                }
//            }
//        }
//
//        System.out.println("No path found.");
//    }
//
//
//
//
//    public String arrayToString(int[] coordinates) {
//        return coordinates[0] + "," + coordinates[1];
//    }
//
//    public int[] stringToArray(String str) {
//        String[] parts = str.split(",");
//        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
//    }
//    public void reconstructPath(String endStr) {
////         this.pathStack = new Stack<>();
////        pathStack.clear();
//
//        String current = endStr;
//
//        while (current != null) {
//            pathStack.push(stringToArray(current));
//            current = cameFrom.get(current);
//        }
//        pathStack.pop();// הוצאת הנתיב ההתחלתי (test)
//
//    }
//
//    public void printMap(int[][] grid) {
//        System.out.println("{");
//        for (int i = 0; i < grid.length; i++) {
//            System.out.print("{");
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j]);
//                if (j < grid[i].length - 1) {
//                    System.out.print(", ");
//                }
//            }
//            System.out.println("}" + (i < grid.length - 1 ? "," : ""));
//        }
//        System.out.println("}");
//    }
//}






//package entity;
//
//
//import main.GamePanel;
//import tile.TileManager;
//import java.util.*;
//
//public class Bfs {
//    GamePanel gamePanel;
//    TileManager tileManager;
//
//
//
//    // גודל הלוח
//    public int ROWS;
//    public int COLS;
//    // תור לבדיקת תאים (BFS)
//    public Queue<String[]> queue;
//
//    public Map<String, String> cameFrom;
//    public Map<String, String> pointBlockedList;
//    Stack< int[] > pathStack;
////    ArrayList<int[]> pointBlockedList;
//
//
//
//
//    // תנועות אפשריות (למעלה, למטה, שמאלה, ימינה)
//    static final int[][] MOVES = {
//            {-1, 0}, // למעלה
//            {1, 0},  // למטה
//            {0, -1}, // שמאלה
//            {0, 1}   // ימינה
//    };
//    public Bfs(GamePanel gamePanel, TileManager tileManager) {
//        this.gamePanel = gamePanel;
//        this.tileManager = tileManager;
//        this.ROWS = gamePanel.maxScreenRow;
//        this.COLS = gamePanel.maxScreenCol;
//        this.queue = new LinkedList<>();
//        this.cameFrom = new HashMap<>();
//        this.pathStack = new Stack<>();
//        this.pointBlockedList = new HashMap<>();
//    }
//
//
//
//
//
//
//    public void findShortestPath(int[][] grid, int[] start, int[] end, boolean flag ) {
//        System.out.println("start: " + Arrays.toString(start));
//        System.out.println("end: " + Arrays.toString(end));
//
//        // איפוס מבנים לכל הרצה חדשה
//        queue.clear();
//        cameFrom.clear();
//
//        // המרת נקודת ההתחלה למחרוזת
//        String startStr = arrayToString(start);
//        System.out.println("startStr: " + startStr);
//
//        // הוספת נקודת ההתחלה לתור עם אבא null
//        // בפעם הראשונה נכנס לדוגמא "0,0"
//        queue.add(new String[]{startStr, null});
//
//        while (!queue.isEmpty()) {
//            // הוצאת התא הראשון מהתור
//            String[] current = queue.poll();
//            String child = current[0];
//            String parent = current[1];
//
//            // שמירת הקשר בין הבן לאבא
//            cameFrom.put(child, parent);
//
//            // אם הגענו לנקודת הסיום, יציאה מהלולאה
//            if (child.equals(arrayToString(end))) {
//                System.out.println("Found path!");
//                reconstructPath(child, flag);
//                return;
//            }
//
//
//
//            // המרת הקואורדינטות בחזרה למערך int
//            int[] currentCoords = stringToArray(child);
//
//            // בדיקת תאים שכנים
//            for (int[] move : MOVES) {
//                int newRow = currentCoords[0] + move[0];
//                int newCol = currentCoords[1] + move[1];
//
//                // בדיקת גבולות הלוח ותאים חסומים
////                if (!flag)
//                if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS &&  !tileManager.tile[grid[newRow][newCol]].isBlocked && (!flag || !isPointInList(newRow, newCol))) {
//                    String neighbor = arrayToString(new int[]{newRow, newCol});
//                    if (!cameFrom.containsKey(neighbor)) {
//                        queue.add(new String[]{neighbor, child});
//                    }
//                }
//            }
//        }
//
//        System.out.println("No path found.");
//    }
//
//
//
//    public void findShortestPath(int[][] grid, int[] start, int[] end){
//       test();
//        findShortestPath(grid, start, end, false);
////        pointBlockedList.clear();
//    }
//
//    public void blockAndFindNewPath(int[][] grid, int[] start, int[] end) {
//        // חיפוש הדרך הראשונה
//        findShortestPath(grid, start, end, false);
//        System.out.println("Blocked points after first path:");
//        printBlockedList();
//
////        pathStack.clear(); // ניקוי המחסנית לפני החיפוש השני
//
//        // חיפוש דרך חדשה
//        findShortestPath(grid, start, end, true);
//        System.out.println("New path avoiding blocked points: " + pathStack.stream()
//                .map(Arrays::toString)
//                .toList());
//    }
//
//
//
//
//    // פונקציה שנועדה לחסום נתיב של רוח
//    private boolean isPointInList(int row, int col) {
//        String check = row + "," + col;
////        if (pointBlockedList.isEmpty()) return true;
//        return (pointBlockedList.containsKey(check));
//    }
//
//
//
////    private void reconstructPath(String endStr,boolean flag) {
//////        pathStack.clear();
////
////        String current = endStr;
////
////        while (current != null) {
////            pathStack.push(stringToArray(current));
////            // במצב של רוח שאמורה להיחסם
////            if (!flag) pointBlockedList.put(current,null);
////            current = cameFrom.get(current);
////        }
////        pathStack.pop();// הוצאת הנתיב ההתחלתי (test)
////
////        System.out.println("Blocked points: " + pointBlockedList.keySet());
////        System.out.println("New path: " + pathStack.stream()
////                .map(Arrays::toString)
////                .toList());
////
////    }
//
//
//    private void reconstructPath(String endStr, boolean flag) {
//        pathStack.clear();
//        String current = endStr;
//
//        while (current != null) {
//            pathStack.push(stringToArray(current));
//
//            if (!flag) { // חסימת נקודות במסלול הראשון בלבד
//                pointBlockedList.put(current, null);
//            }
//
//            current = cameFrom.get(current);
//        }
//
//        if (!pathStack.isEmpty()) {
//            pathStack.pop(); // הסרת נקודת ההתחלה מהמחסנית
//        }
//
//        System.out.println("Blocked points: " + pointBlockedList.keySet());
//        System.out.println("Path in stack: " + pathStack.stream()
//                .map(Arrays::toString)
//                .toList());
//    }
//
//
//    private void printBlockedList() {
//        System.out.println("Currently blocked points:");
//        for (String point : pointBlockedList.keySet()) {
//            System.out.println(point);
//        }
//    }
//
//    private void test(){
////        Iterator<int[]> iterator = pathStack.iterator();
////        while (iterator.hasNext()) {
////            int[] test = iterator.next();
//
//
//
//        for (String key : pointBlockedList.keySet()) {
//            int [] test = stringToArray(key);
//            tileManager.getMap()[test[0]][test[1]] = 5;
//        }
//
//    }
//
//
//    private String arrayToString(int[] coordinates) {
//        return coordinates[0] + "," + coordinates[1];
//    }
//
//    private int[] stringToArray(String str) {
//        String[] parts = str.split(",");
//        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
//    }
//
//    public void printMap(int[][] grid) {
//        System.out.println("{");
//        for (int i = 0; i < grid.length; i++) {
//            System.out.print("{");
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j]);
//                if (j < grid[i].length - 1) {
//                    System.out.print(", ");
//                }
//            }
//            System.out.println("}" + (i < grid.length - 1 ? "," : ""));
//        }
//        System.out.println("}");
//    }
//}







