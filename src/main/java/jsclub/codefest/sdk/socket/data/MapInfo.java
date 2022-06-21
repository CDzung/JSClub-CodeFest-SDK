package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;
import jsclub.codefest.sdk.model.Hero;
import java.util.ArrayList;
import java.util.List;

public class MapInfo {
    public String myId;
    public MapSize size;
    public List<Player> players;
    public List<int[]> map;
    public List<Bomb> bombs;
    public List<Spoil> spoils;
    public List<Gift> gifts;
    public List<Viruses> viruses;
    public List<Human> human;
    public List<Node> walls = new ArrayList<>();
    public List<Node> boxs = new ArrayList<>();
    public List<Node> blank = new ArrayList<>();
    public List<Node> selfisolatedZone = new ArrayList();

    public Player getPlayerByKey(String key) {
        Player player = null;
        if (players != null ) {
            for (Player p : players) {
                if (key.startsWith(p.id)) {
                    player = p;
                    break;
                }
            }
        }
        return player;
    }

    public List<Viruses> getVirus() {
        return viruses;
    }

    public List<Human> getDhuman() {
        List<Human> dhumanList = new ArrayList<>();
        if(human!=null) {
            for (Human dhuman : human) {
                if (dhuman.infected) {
                    dhumanList.add(dhuman);
                }
            }
        }
        return dhumanList;
    }

    public List<Human> getNHuman() {
        List<Human> nhumanList = new ArrayList<>();
        if(human!=null) {
            for (Human nhuman : human) {
                if (!nhuman.infected && nhuman.curedRemainTime == 0) {
                    nhumanList.add(nhuman);
                }
            }
        }
        return nhumanList;
    }

    public Player getEnemy() {
        for (Player player : players) {
            if (!Hero.getPlayerName().startsWith(player.id)) {
                return player;
            }
        }
        return null;
    }

    public int[][] getMap() {
        int[][] map = new int[size.rows][size.cols];
        for (int i = 0; i < size.rows; i++) {
            map[i] = this.map.get(i);
            for (int j = 0; j < size.cols; j++) {
                if (map[i][j] == 0) {
                    blank.add(new Node(j,i));
                } else if (map[i][j] == 1) {
                    walls.add(new Node(j,i));
                } else if (map[i][j] == 2) {
                    boxs.add(new Node(j,i));
                } else if (map[i][j] == 6 || map[i][j] == 7) {
                    selfisolatedZone.add(new Node(j,i));
                } else {
                    walls.add(new Node(j,i));
                }

            }
        }
        return map;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
