import java.util.Random;
import java.util.Scanner;

public class hw4 {
    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);
    //Map
    public static char[][] map;
    public static int mapWidth;
    public static int mapHeight;
    public static int mapSizeMin = 3;
    public static int mapSizeMax = 6;
    public static char empty = '_';
    public static char ready = '*';

    //Player
    public static char player = '@';
    public static String name = "Boris";
    public static int playerHP = 100;
    public static int playerStr =15;
    public static int playerX;
    public static int playerY;
    public static int playerExp;
    public static final int playerMoveUp = 8;
    public static final int playerMoveLeft = 4;
    public static final int playerMoveRight = 6;
    public static final int playerMoveDown = 2;

    //Trap
    public static char trap = 'T';
    public static int trapAttack;
    public static int trapCount;
    public static int trapValueMin = 5;
    public static int trapValueMax =15;

    public static void main(String[] args) {
        createMap();
        spawnPlayer();
        spawnTrap();
        while (true) {
            showMap();
            movePlayer();

            if (!isPlayerAlive()) {
                System.out.println(name + " is dead");
                break;
            }

            if (isFullMap()) {
                System.out.println(name + " win this map");
                break;
            }
        }
        System.out.println("GAME OVER");


    }

    public static void createMap(){
        mapWidth = randomValue(mapSizeMin, mapSizeMax) ;
        mapHeight = randomValue(mapSizeMin, mapSizeMax) ;
        map = new char [mapHeight][mapWidth];

        for (int y = 0; y < mapHeight; y++){
            for (int x = 0; x < mapWidth; x++) {
                map[y][x] = empty;
            }
        }
        System.out.println("Map has been created. Map size is " + mapWidth + "x" + mapHeight);
    }

    public static void showMap() {
        System.out.println("============>MAP<============");
        for (int y = 0; y < mapHeight; y++){
            for (int x = 0; x < mapWidth; x++) {
                System.out.print(map[y][x] + "|");
            }
            System.out.println();
        }
        System.out.println("=============================");
    }
    public static void spawnPlayer(){
        playerX = randomValue(0, mapWidth-1);
        playerY = randomValue(0, mapHeight-1);
        map[playerY][playerX] = player;
        System.out.println(name + " has spawn in [" + (playerX +1) + ":" + (playerY +1) + "]");

    }
    public static void spawnTrap(){
       trapAttack = randomValue(trapValueMin, trapValueMax);
       trapCount = (mapWidth + mapHeight) /2;
       int trapX;
       int trapY;
       for (int i = 1; i<=trapCount; i++){
           do {
               trapX = random.nextInt(mapWidth);
               trapY = random.nextInt(mapHeight);
           }while (!isEmpty(trapX, trapY));
           map[trapY][trapX] = trap;
       }
        System.out.println(trapCount + " traps has been created. Traps Attack = " +trapAttack);
    }
    public static void movePlayer() {

        int currentPlayerX = playerX;
        int currentPlayerY = playerY;

        int playerDestination;

        do {
            System.out.print("Enter your move: (Up: " + playerMoveUp + " | Down: " + playerMoveDown +
                    " | Left: " + playerMoveLeft + " | Right: " + playerMoveRight + ") >>> ");

            playerDestination = scanner.nextInt();

            switch (playerDestination) {
                case playerMoveUp:
                    playerY -= 1;
                    break;
                case playerMoveDown:
                    playerY += 1;
                    break;
                case playerMoveLeft:
                    playerX -= 1;
                    break;
                case playerMoveRight:
                    playerX += 1;
                    break;
            }

        } while (!checkValidMove(currentPlayerX, currentPlayerY, playerX, playerY));

        playerMoveAction(currentPlayerX, currentPlayerY, playerX, playerY);

    }

    public static boolean isEmpty(int x, int y){
        return map[y][x] == empty;
    }
    public static boolean isFullMap() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map[y][x] == empty) return false;
            }
        }
        return true;
    }
    public static boolean checkValidMove(int pastX, int pastY, int nextX, int nextY) {
        if (nextX >= 0 && nextX < mapWidth && nextY >= 0 && nextY < mapHeight) {
            System.out.println(name + " move to [" + (nextX + 1) + ":" + (nextY + 1) + "] success");
            return true;
        } else {
            System.out.println(name + " move invalid! Please try again!");
            playerX = pastX;
            playerY = pastY;
            return false;
        }
    }

    public static void playerMoveAction(int pastX, int pastY, int nextX, int nextY) {
        if (map[nextY][nextX] == trap) {
            playerHP -= trapAttack;
            trapCount--;
            System.out.println("Alarm! " + name + " has been attack. HP = " + playerHP);
        }

        map[nextY][nextX] = player;
        map[pastY][pastX] = ready;

    }

    public static int randomValue(int value1, int value2) {
        return value1 + random.nextInt(value2 - value1 +1);
    }
    public static boolean isPlayerAlive() {
        return playerHP > 0;
    }
}
