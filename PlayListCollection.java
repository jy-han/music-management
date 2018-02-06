package han.playmanage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Player class 
 * property: list map。
 * methods：add, delete search show list。
 */
public class PlayListCollection implements Serializable {
	Scanner input = new Scanner(System.in);

	// property: list map
	private Map<String, PlayList> playListMap;

	// constructor
	public PlayListCollection() {

	}

	public PlayListCollection(Map<String, PlayList> playListMap) {
		this.setPlayListMap(playListMap);
	}

	// getter, setter
	public Map<String, PlayList> getPlayListMap() {
		if (this.playListMap == null) 
			this.playListMap = new HashMap<>();
		return playListMap;
	}

	public void setPlayListMap(Map<String, PlayList> playListMap) {
		this.playListMap = playListMap;
	}

	// add list
	public void addPlayList(String listName, PlayList playList) {
		String answer;
		boolean flag = true, check = true;
		
		do {
			check = true;
			try {
				System.out.println("add number of songs："); 
				int addNum = input.nextInt();
				for (int i = 0; i < addNum; i++) { 
					System.out.println("The " + (i + 1) + "song：\nenter song id："); 
					String addId = input.next();
					if (playList.getMusicList().contains(playList.searchSongById(addId)) == false) { 
						System.out.println("the song is not exist！\nenter song name："); //if the song is not exist,continue enter
						String addMusicName = input.next();
						System.out.println("enter singer："); 
						String addSinger = input.next();
						playList.addToPlayList(new Song(addId, addMusicName, addSinger)); 
						this.getPlayListMap().get(listName).addToPlayList(playList.searchSongById(addId)); 
					} else {
						do {
							System.out.println("The song is exist, add to " + listName + "list？ Y?N"); 
							answer = input.next();
							flag = true;

							if (answer.equals("Y") || answer.equals("y")) { 
								this.getPlayListMap().get(listName).addToPlayList(playList.searchSongById(addId));
							} else if (answer.equals("N") || answer.equals("n")) { 
								System.out.println("Enter the song to add！");
								i--;
								break;
							} else {
								System.out.println("Input error！"); 
								flag = false;
							}
						} while (flag == false);
						continue;
					}
				}
			} catch (InputMismatchException e) { 
				System.out.println("Input error！");
				check = false;
				input.next();
				continue;
			}
		} while (check == false);

		System.out.println("main list：");
		playList.displayAllSong();
		System.out.println("normal list：");
		this.getPlayListMap().get(listName).displayAllSong();
	}

	// delete list
	public void deletePlayList(PlayList playList) {
		System.out.println("delete list name："); 
		String deleteName = input.next();
		if (this.getPlayListMap().containsKey(deleteName)) { 
			this.getPlayListMap().remove(deleteName); 
			System.out.println("deleted！");
		} else {
			System.out.println("the list is not exist！"); 
		}
	}

	// search list by name
	public PlayList searchPlayListByName(String name) {
		if (this.getPlayListMap().containsKey(name)) { 
			System.out.println("list exist！\nlist name：" + name);
			this.getPlayListMap().get(name).displayAllSong();
		}
		return this.getPlayListMap().get(name);
	}

	// show all lists
	public void displayPlayListName() {
		Iterator<String> it = this.getPlayListMap().keySet().iterator(); 
		while (it.hasNext())
			System.out.println(it.next());
	}

	// output list
	public void outputPlayList() {
		System.out.println("number of list to output："); 
		String outputList = input.next() + "list.txt"; 
		try {
			File file = new File(outputList); 
			file.createNewFile(); 
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); 
			oos.writeObject((this.getPlayListMap().get(outputList))); 
			oos.flush(); 
			oos.close(); 
			System.out.println("歌单导出成功！"); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}
