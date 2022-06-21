package jsclub.codefest.sdk.model;

import jsclub.codefest.sdk.constant.ClientConfig;
import jsclub.codefest.sdk.socket.data.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bomberman {

    private Node position;

    private Node targetBox;

    private int bombPower = 1;
    public int mySpeed = 0;
    public int bombDelay = 0;
    public int myPill = 0;


    public Player metadata = new Player();
    public List<Node> bombs = new ArrayList<>();
    public List<Node> dangerBombs = new ArrayList<>();
    public List<Node> myBombList = new ArrayList<>();
    public List<Node> virusList = new ArrayList<>();
    public List<Node> dangerHumanList = new ArrayList<>();
    public List<Node> normalHumanList = new ArrayList<>();
    public List<Node> boxs = new ArrayList<>();
    //    public List<Node> boxsGifts = new ArrayList<>();
    public List<Node> boxsCanBreak = new ArrayList<>();
    public List<Node> walls = new ArrayList<>();
    public List<Node> listShouldEatSpoils = new ArrayList<>();
    //    public List<Node> listGift = new ArrayList<>();
//    public List<Node> listNoEatSpoils = new ArrayList<>();
    public List<Node> selfisolatedZone = new ArrayList<>();

    public Player mEnemyPlayer;

    public void initPlayerInfo(Player myPlayer, MapInfo mapInfo) {
        metadata = myPlayer;
        setPosition(Node.createFromPosition(myPlayer.currentPosition));
        setPlayerStatus(myPlayer.power, myPlayer.speed, myPlayer.delay, myPlayer.pill);
        setBlankPlace(mapInfo.blank);
        filterSpoils(mapInfo);
        List<Node> boxList = new ArrayList<>(mapInfo.boxs);
        Player enemy = mapInfo.getEnemy();
        if (enemy != null) {
            Node enemyPosisiton = Node.createFromPosition(enemy.currentPosition);
//            boxList.add(enemyPosisiton);
            setEnemyPlayer(enemy);
        }
        setBoxs(boxList);
//        setBoxsGifts(mapInfo.boxsGift);
        setWalls(mapInfo.walls);
        setSelfisolatedZone(mapInfo.selfisolatedZone);
        setNormalHumanList(mapInfo.getNHuman());
        setVirusLists(mapInfo.getVirus(), true);
        setDangerHumanList(mapInfo.getDhuman(), true);
        setBombs(mapInfo.bombs, enemy.power, true);
    }

    public Player getEnemyPlayer() {
        return mEnemyPlayer;
    }

    public void setEnemyPlayer(Player mEnemyPlayer) {
        this.mEnemyPlayer = mEnemyPlayer;
    }

    public void setWalls(List<Node> walls) {
        this.walls = new ArrayList<>(walls);
    }

    public List<Node> getWalls() {
        return walls;
    }

    public void setBoxs(List<Node> boxs) {
        this.boxs = new ArrayList<>(boxs);
        this.boxsCanBreak = new ArrayList<>(boxs);
    }

    public void updateBoxV(Node box) {
        for (Node node : this.boxsCanBreak) {
            if (node.equals(box)) {
                node.setV(box.getV());
            }
        }
    }

    public void setSelfisolatedZone(List<Node> selfisolatedZone) {
        this.selfisolatedZone = selfisolatedZone;
    }

    public List<Node> getSelfisolatedZone() {
        return selfisolatedZone;
    }

//        public void setBoxsGifts(List<Node> boxsGifts) {
//        this.boxsGifts = new ArrayList<>(boxsGifts);
//    }

    public void setVirusLists(List<Viruses> mvirusList, boolean isImportant) {
        this.virusList.clear();
        if (mvirusList != null) {
            for (Viruses v : mvirusList) {
                Node newNode = Node.createFromPosition(v.position);
                this.virusList.add(newNode);
                if (isImportant) {
                    this.virusList.addAll(newNode.getAllPosition(v.position, v.direction));
                }
//                else {
//                    this.virusList.addAll(v.position.getAllPosition(v.position,v.direction));
//                }
            }
            mBlankPlace.removeAll(virusList);
        }
    }

    public List<Node> getVirusLists() {
        return virusList;
    }

    public void setDangerHumanList(List<Human> mdangerHumanList, boolean isImportant) {
        this.dangerHumanList.clear();
        if (mdangerHumanList != null) {
            for (Human h : mdangerHumanList) {
                Node newNode = Node.createFromPosition(h.position);
                this.dangerHumanList.add(newNode);
                if (isImportant) {
                    this.dangerHumanList.addAll(newNode.getAllPosition(h.position, h.direction));
                }
//                else {
//                    this.dangerHumanList.addAll(h.position.getAllPosition(h.position,h.direction));
//                }
            }
            mBlankPlace.removeAll(dangerHumanList);
        }
    }

    public List<Node> getdangerHumanLists() {
        return dangerHumanList;
    }

    public void setNormalHumanList(List<Human> mNormalHumanList) {
        this.normalHumanList.clear();
        if (mNormalHumanList != null) {
            for (Human h : mNormalHumanList) {
                Node newNode = Node.createFromPosition(h.position);
                this.normalHumanList.add(newNode);
            }
        }
    }

    public List<Node> getnormalHumanLists() {
        return normalHumanList;
    }


    public List<Node> getBoxs() {
        return boxs;
    }

    private Set<String> mRestrictedNodes = new HashSet<>();

    public Bomberman() {
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public Node getPosition() {
        return position;
    }

    public void setRestrictedNodes(List<Node> s) {
        mRestrictedNodes.clear();//first remove the old station site
        addRestrictedNodes(s);
    }

    public void addRestrictedNodes(List<Node> s) {
        if (s != null) {
            for (Node n : s) {
                mRestrictedNodes.add(n.toString());
            }
            mBlankPlace.removeAll(s);
        }
    }

    public void setPlayerStatus(int power, int speed, int delay, int pill) {
        bombPower = power;
        mySpeed = speed;
        bombDelay = delay;
        myPill = pill;
    }

    private List<Node> mBlankPlace = new ArrayList<>();

    public void setBlankPlace(List<Node> blankPlace) {
        for (Node node : blankPlace) {
            node.setV(getBlankV(node, blankPlace));
            mBlankPlace.add(node);
        }
    }

    private double getBlankV(Node n, List<Node> blankPlace) {
        double v = 1;
        List<Node> listCheck = new ArrayList<>();
        listCheck.add(n.leftPosition(1));
        listCheck.add(n.rightPosition(1));
        listCheck.add(n.upPosition(1));
        listCheck.add(n.downPosition(1));
        for (Node nodeCheck : listCheck) {
            if (blankPlace.contains(nodeCheck)) {
                v++;
            }
        }
        return v;
    }

    public List<Node> getBlankPlace() {
        return mBlankPlace;
    }

    public int getBombPower() {
        return bombPower;
    }

    public void setBombs(List<Bomb> bombs, int enemyPower, boolean isNeedEffectBomb) {
        this.bombs.clear();
        this.myBombList.clear();
        int power;
        int checkBomSafeTime = ClientConfig.BOMB_DELAY - mySpeed - ClientConfig.ADDITIONAL_DANGER_TIME;
        for (Bomb b : bombs) {
            this.bombs.add(Node.createFromPosition(b));
            dangerBombs.add(Node.createFromPosition(b));
            if (Hero.getPlayerName().startsWith(b.playerId)) {
                power = bombPower;
                myBombList.add(Node.createFromPosition(b));
            } else {
                power = enemyPower;
            }
            if (isNeedEffectBomb) {
                List<Node> effectBombList = getEffectBombExplosed(b, power);
                listShouldEatSpoils.removeAll(effectBombList);
                dangerBombs.addAll(effectBombList);
                if (b.remainTime > checkBomSafeTime) {
                    continue;
                }
                this.bombs.addAll(effectBombList);
            }
        }
        mBlankPlace.removeAll(dangerBombs);
    }

    public List<Node> getEffectBombExplosed(Position b, int power) {
        List<Node> listEffectBomb = new ArrayList<>();
        //Add bomb affect
        for (int direction = 1; direction < 5; direction++) {

            for (int i = 1; i <= power; i++) {
                Node affectedNode = Node.createFromPosition(b).nextPosition(direction, i);
                listEffectBomb.add(affectedNode);
                if (walls.contains(affectedNode) || boxs.contains(affectedNode)) {
                    if (boxs.contains(affectedNode)) {
                        boxsCanBreak.remove(affectedNode);
                    }
                    break;
                }
            }
        }

        return listEffectBomb;
    }

    public void filterSpoils(MapInfo info) {
        listShouldEatSpoils.clear();
//        listNoEatSpoils.clear();
        for (Spoil spoil : info.spoils) {
            Node spoilsNode = Node.createFromPosition(spoil);
            if (spoil.spoil_type == 5) {
                spoilsNode.setV(6);
            } else {
                spoilsNode.setV(5);
            }
            listShouldEatSpoils.add(spoilsNode);
        }

// add Gift to eat list
//        for (Gift gift : info.gifts) {
//            Node giftsNode = new Node(gift.getX(), gift.getY());
//            giftsNode.setV(10);
//            listGift.add(giftsNode);
//        }
//
    }

//    public boolean shouldEatFood(Node thatNode) {
//        int count = 0;
//        int x = thatNode.getX();
//        int y = thatNode.getY();
//        for(int i=-1; i<=1; i++) {
//            for (int j=-1; j<=1; j++) {
//                if(virusList.contains(new Node(x+i, y+j)) || dangerHumanList.contains(new Node(x+i, y+j))){
//                    count++;
//                }
//            }
//        }
//        return true;
//    }


    public List<Node> getBombs() {
        return bombs;
    }

    public void setVirtualBombs(Node virtualBomb) {
        List<Node> virtualBombEffect = getEffectBombExplosed(virtualBomb, bombPower);
        bombs.add(virtualBomb);
        dangerBombs.addAll(virtualBombEffect);
        mBlankPlace.removeAll(virtualBombEffect);
    }

    public void setTargetBox(Node node) {
        targetBox = node;
    }

    public Node getTargetBox() {
        return targetBox;
    }

    public List<Node> getPlacingBom(Node targetBox, boolean isNearPlaceBomb) {
        List<Node> placeBomb = new ArrayList<>();
        if (isNearPlaceBomb) {
            int checkColMin = targetBox.getX() - 1;
            int checkRowsMin = targetBox.getY() - 1;
            for (int i = checkColMin; i <= targetBox.getX() + 1; i++) {
                for (int j = checkRowsMin; j <= targetBox.getY() + 1; j++) {
                    Node n = new Node(i, j);
                    if (!mRestrictedNodes.contains(n.toString()) && !boxs.contains(n)
                            && (n.getX() == targetBox.getX() || n.getY() == targetBox.getY())) {
                        placeBomb.add(n);
                    }
                }
            }
        } else {
            //Add bom place
            for (int direction = 1; direction <= 4; direction++) {
                List<Node> tempPlace = new ArrayList<>();
                for (int i = 1; i <= bombPower; i++) {
                    Node affectedNode = targetBox.nextPosition(direction, i);
                    if (walls.contains(affectedNode) || boxs.contains(affectedNode)) {
                        break;
                    }
                    tempPlace.add(affectedNode);

                }
                placeBomb.addAll(tempPlace);
            }
        }
        return placeBomb;
    }


    public Set<String> getRestrictedNodes() {
        return mRestrictedNodes;
    }

    public boolean isEndanger() {
        return (dangerBombs.contains(position) || virusList.contains(position) || dangerHumanList.contains(position));
    }

    public static Bomberman clone(Bomberman p) {
        Bomberman cloneBomb = new Bomberman();
        cloneBomb.position = p.getPosition();
        cloneBomb.mBlankPlace = new ArrayList<>(p.getBlankPlace());
        cloneBomb.bombs = new ArrayList<>(p.getBombs());
        //cloneBomb.boxsGifts = new ArrayList<>(p.boxsGifts);
        cloneBomb.boxs = new ArrayList<>(p.getBoxs());
        cloneBomb.boxsCanBreak = new ArrayList<>(p.boxsCanBreak);
        cloneBomb.mRestrictedNodes = new HashSet<>(p.getRestrictedNodes());
        cloneBomb.bombPower = p.getBombPower();
        cloneBomb.listShouldEatSpoils = new ArrayList<>(p.listShouldEatSpoils);
        //cloneBomb.listNoEatSpoils = new ArrayList<>(p.listNoEatSpoils);
        cloneBomb.dangerBombs = new ArrayList<>(p.dangerBombs);
        cloneBomb.virusList = new ArrayList<>(p.virusList);
        cloneBomb.dangerHumanList = new ArrayList<>(p.dangerHumanList);
        cloneBomb.walls = new ArrayList<>(p.getWalls());
        //cloneBomb.listGift = new ArrayList<>(p.listGift);
        cloneBomb.setEnemyPlayer(p.getEnemyPlayer());
        cloneBomb.myBombList = new ArrayList<>(p.myBombList);
        cloneBomb.selfisolatedZone = new ArrayList<>(p.selfisolatedZone);
        return cloneBomb;
    }

    public boolean
    isBombAvailable(long lastPlacingTime) {
        long time = System.currentTimeMillis() - lastPlacingTime;
        return time >= bombDelay;
    }
}
