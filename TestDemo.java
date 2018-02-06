package han.playtest;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import han.playmanage.PlayList;
import han.playmanage.PlayListCollection;
import han.playmanage.Song;

public class TestDemo {
	Scanner input = new Scanner(System.in);
	int number;
	PlayList mainPlayList = new PlayList();
	PlayListCollection plc = new PlayListCollection();

	/*
	 *  test song class
	 *  1. compare two song objects whether they are the some
	 *  2. show the information about the two song
	 */
	public void testSong() {
		// create two song object
		Song song1 = new Song("s01", "song1", "singer1");
		Song song2 = new Song("s02", "song2", "singer2");
		// compare song1 and song2
		System.out.println("song1 == song2 ?" + song1.equals(song2));
		// show information of the song
		System.out.println(song1 + "\n" + song2);
	}

	/*
	 *  test player class
	 *  1. create play list, add song in list
	 *  2. show all songs in the list
	 *  3. search song in list by id
	 *  4. search song in list by name
	 *  5. revise song in list and show all songs 
	 *  6. delete song from list
	 */
	public void testPlayList() {
		// create a play list object, and add songs
		PlayList pl = new PlayList();
		pl.addToPlayList(new Song("s01", "sing1", "singer1"));
		pl.addToPlayList(new Song("s02", "sing2", "singer2"));
		// show all songs in the list
		pl.displayAllSong();
		// search song by id
		System.out.println(pl.searchSongById("s01") != null ? "The song's information is：\n" + pl.searchSongById("s01") : "This song is not exist！");
		// search song by name
		pl.searchSongByName("song1");
		// revise song
		System.out.println("after revise：");
		pl.updateSong("s01", new Song("s03", "song3", "singer3"));
		pl.displayAllSong();
		// delete song
		System.out.println("after delete：");
		pl.deleteSong("s02");
		pl.displayAllSong();
	}

	/*
	 * test player class
	 * 1. add play list
	 * 2. search list by name
	 * 3. show all lists in player
	 * 4. delete a list
	 * 5. output a list 
	 */
	public void testPlayListCollection() {
		PlayList pl = new PlayList();
		pl.addToPlayList(new Song("s01", "music1", "singer1"));
		pl.addToPlayList(new Song("s02", "music2", "singer2"));
		pl.addToPlayList(new Song("s03", "music3", "singer3"));
		PlayListCollection plc = new PlayListCollection();
		// add list
		plc.addPlayList("test", pl);
		plc.addPlayList("test", pl);

		// search list by name
		plc.searchPlayListByName("list1");
		// show all lists
		plc.displayPlayListName();
		// deleted list
		plc.deletePlayList(pl);
		plc.displayPlayListName();
		// output the list
		plc.outputPlayList();
	}

	// main menu
	public void mainManu() {
		System.out.println("*********************************************");
		System.out.println("               **main menu**");
		System.out.println("               1--manage the play list");
		System.out.println("               2--manage the player");
		System.out.println("               0--exit");
		System.out.println("*********************************************");
	}

	// 播放列表管理菜单
	public void playListMenu() {
		int addMusicNum;
		do {
			System.out.println("*********************************************");
			System.out.println("          **play list management**");
			System.out.println("          1--add song to the main list");
			System.out.println("          2--add song to the normal list");
			System.out.println("          3--search song by id");
			System.out.println("          4--search song by name");
			System.out.println("          5--updete the song");
			System.out.println("          6--delete a song");
			System.out.println("          7--show all songs");
			System.out.println("          8--output the list");
			System.out.println("          9--return to upper menu");
			System.out.println("*********************************************");
			try {
				boolean flag = false;  //judge whether to loop
				System.out.println("Please enter the number from the menu：");  //get the number from the menu
				number = input.nextInt();
				switch (number) {
				case 1:
					while (flag == false) {
						flag = true;
						try {
							System.out.println("add song to the main menu\nEnter the number of songs which to add：");
							addMusicNum = input.nextInt();
							for (int i = 0; i < addMusicNum; i++) {  //get song's information by loop all songs
								System.out.println("Enter the " + (i + 1) + "song：\nEnter the id of the song：");
								String addMusicId = input.next();
								if (mainPlayList.searchSongById(addMusicId) != null) {  //judge whether the song is exist
									System.out.println("The song is exist！");  //imply the song is exist
									i--;continue;  //continue the next loop
								}
								System.out.println("Enter the name of the song:");   //get name of the song
								String addMusicName = input.next();
								System.out.println("Enter the singer：");  //get singer information
								String addMusicSinger = input.next();
								Song addSong = new Song(addMusicId, addMusicName, addMusicSinger);   //create song object
								mainPlayList.addToPlayList(addSong);  //add song to the main list
							}
						} catch (InputMismatchException e) {  //catch the error input
							System.out.println("Input error, pLease enter again！");
							input.next();
							flag = false;continue;
						}
					}
					if (flag)
						plc.getPlayListMap().put("Main list", mainPlayList);  //if true, add list and list's name to map
					break;
				case 2:
					System.out.println("add song to normal list");  
					System.out.println("Enter the name of the song：");   //get list's name
					String normalListName = input.next();  
					if(plc.getPlayListMap().containsKey(normalListName))
						plc.addPlayList(normalListName, mainPlayList);  
					else
						System.out.println("The list is not exist, please add list first by manage the player!");  
					break;
				case 3:
					System.out.println("search song by id\nEnter the name of the list：");   
					String searchListId = input.next();
					if (plc.getPlayListMap().containsKey(searchListId)) {   //if list is exist
						System.out.println("Enter the id of the song：");  //search the id
						String searchMusicId = input.next();
						if ((plc.getPlayListMap().get(searchListId).searchSongById(searchMusicId) != null)) {  //if the song is exist, give hint
							System.out.println("The song is" + plc.getPlayListMap().get(searchListId).searchSongById(searchMusicId));  
						} else
							System.out.println("The song " + searchListId + "not exist！");  //give hint
					} else
						System.out.println(searchListId+"list is not exist！ ");  //hint the list not exist
					break;
				case 4:
					System.out.println("search song by name\nEnter the list name：");  //get list name
					String searchListName = input.next();
					if (plc.getPlayListMap().containsKey(searchListName)) {  //judge whether the list is exist
						System.out.println("Name of the song：");  //get the name of the song
						String searchMusicName = input.next();
						plc.getPlayListMap().get(searchListName).searchSongByName(searchMusicName);  //search the song
					} else 
						System.out.println(searchListName + "list is not exist！");  //hint the list is not exist
					break;
				case 5:
					System.out.println("revise song\nEnter the list name：");  //get list name
					String reviseList = input.next();
					if (plc.getPlayListMap().containsKey(reviseList)) {   //if the list is exist
						System.out.println("Enter the song's id：");  //search song by id
						String reviseMusicId = input.next();
						if ((plc.getPlayListMap().get(reviseList).searchSongById(reviseMusicId) != null)) {  //if the song is exist, revise it  
							plc.getPlayListMap().get(reviseList).updateSong(reviseMusicId, plc.getPlayListMap().get(reviseList).searchSongById(reviseMusicId));
						} else
							System.out.println("The song in" + reviseList + "list is not exist！");  //hint the song is not exist
					} else
						System.out.println(reviseList+"list is not exist！ ");  //show the list is not exist
					break;
				case 6:
					System.out.println("delete the song from list\nEnter the list name：");   //get song id
					String deleteList = input.next();
					if (plc.getPlayListMap().containsKey(deleteList)) {   //if list is exist
						System.out.println("Enter the song id：");  //get song id
						String deleteMusicId = input.next();
						if ((plc.getPlayListMap().get(deleteList).searchSongById(deleteMusicId) != null)) {  //if the song exist, revise it
							Iterator<PlayList> it = plc.getPlayListMap().values().iterator();
							while(it.hasNext())
								it.next().deleteSong(deleteMusicId);
							System.out.println("already deleted！");  //deleted
						} else
							System.out.println("The song in" + deleteList + "list is not exist！");  
					} else
						System.out.println(deleteList+"list is not exist！ ");  
					break;
				case 7:
					System.out.println("show all songs in the list\nEnter the list name：");   
					String displayListName = input.next(); 
					if (displayListName.equals("The main list"))   
						mainPlayList.displayAllSong();
					else if (plc.getPlayListMap().containsKey(displayListName))   
						plc.getPlayListMap().get(displayListName).displayAllSong();
					else
						System.out.println("the list is not exist！");  
					break;
				case 8:
					System.out.println("output the list");  //output the list
					plc.outputPlayList();
					break;
				case 9:break; 
				default:
					System.out.println("Number error, enter again！");  //input number error
				}
			} catch (InputMismatchException e) {
				System.out.println("Input error, enter again！");   
				input.next();
				continue;
			}
		} while (number != 9);  
	}

	// player menu
	public void playerMenu() {
		do {
			try {
				System.out.println("*********************************************");
				System.out.println("          **player management**");
				System.out.println("          1--add list to player");
				System.out.println("          2--delete list to player");
				System.out.println("          3--search list by name");
				System.out.println("          4--show all list");
				System.out.println("          9--return");
				System.out.println("*********************************************");
				System.out.println("Enter the number：");
				number = input.nextInt();
				switch (number) {
				case 1:
					System.out.println("add list");  
					System.out.println("Enter list name：");  
					String addListName = input.next();  
					if (plc.getPlayListMap().containsKey(addListName))   
						System.out.println("Player list is already exist！");
					else {
						PlayList addPlayList = new PlayList();  
						plc.getPlayListMap().put(addListName, addPlayList);  
					}
					break;
				case 2:
					System.out.println("delete list");  
					plc.deletePlayList(mainPlayList);break; 
				case 3:
					System.out.println("search list by name\nEnter the list name：");  
					String searchListName = input.next();
					plc.searchPlayListByName(searchListName);break;  
				case 4:
					System.out.println("show all lists\nlist name：");  
					plc.displayPlayListName();break;  
				case 9:break;  
				default:
					System.out.println("Number error, please enter again！");  
				}
			} catch (InputMismatchException e) {
				System.out.println("Input error！");  
				input.next();  
				continue; 
			}
		} while (number != 9);  

	}

	// the main flow
	public void test() {
		do {
			mainManu();  
			try {
				System.out.println("Enter a number to manage：");   
				number = input.nextInt();
				switch (number) {
				case 1:
					playListMenu();  
					break;
				case 2:
					playerMenu(); 
					break;
				case 0:
					System.out.println("exit management！");   
					System.exit(-1);
				default:
					System.out.println("Number error！");  
				}
			} catch (InputMismatchException e) {
				System.out.println("input error！");  
				input.next();
				continue;
			}
		} while (number != 0);  
	}

	public static void main(String[] args) {
		TestDemo td = new TestDemo();
		// td.testSong(); //test song class
		// td.testPlayList(); //test playlist class
		// td.testPlayListCollection(); //test player class
		td.test();  //test the main flow
	} 

}
