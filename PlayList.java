package han.playmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/**
 * Play list class:
 * property：list name、song list。
 * method：add, search, revise, delete song from list。
 */
public class PlayList {
	Scanner input = new Scanner(System.in);
	//property：list name、song list
	private String playListName;
	private List<Song> musicList;
	
	//constructor
	public PlayList(){
		
	}
	public PlayList(String playListName, List<Song> musicList){
		this.setPlayListName(playListName);
		this.setMusicList(musicList);
	}
	//getter,setter
	public String getPlayListName() {
		return playListName;
	}
	public void setPlayListName(String playListName) {
		this.playListName = playListName;
	}
	public List<Song> getMusicList() {
		if(this.musicList == null)
			this.musicList = new ArrayList<Song>();
		return this.musicList;
	}
	public void setMusicList(List<Song> musicList) {
		this.musicList = musicList;
	}
	
	//add song to list
	public void addToPlayList(Song song){
		if (this.getMusicList().contains(song))  //check whether the song is exist
			System.out.println("Song exist！");
		else
			this.getMusicList().add(song);			 
	} 
	
	//show all songs
	public void displayAllSong(){
		System.out.println("All songs：");
		for(Song song : this.getMusicList())  
			System.out.println(song);
	}
	
	//search song by id
	public Song searchSongById(String id){
		Song temp, song = null;
		Iterator<Song> it = this.getMusicList().iterator();  
		while(it.hasNext()) {
			temp = (Song)it.next();
			if(temp.getId().equals(id)){
				song = temp;
			}
		}
		return song;
	}
	
	//search song by name
	public Song searchSongByName(String name){
		boolean flag = false;
		Song temp;
		Iterator<Song> it = this.getMusicList().iterator();
		while(it.hasNext()) {
			temp = (Song)it.next();
			if(temp.getName().equals(name)){
				flag = true;
				System.out.println("the song is：\n"+temp);
			}
		}
		if(flag == false)
			System.out.println("Song not exist！");
		return null;
	}
	
	//revise song 
	public void updateSong(String id, Song song){
		System.out.println("new name："); 
		String revisedMusicName = input.next();
		System.out.println("new singer："); 
		String revisedSinger = input.next();
		song.setName(revisedMusicName);  
		song.setSinger(revisedSinger);  
		if(this.getMusicList().contains(id)) { 
			this.getMusicList().remove(searchSongById(id));
			this.getMusicList().add(song);
		}
		System.out.println("updated！");
	}
	
	//delete song from list
	public void deleteSong(String id){
		if(this.getMusicList().contains(searchSongById(id)))  
			this.getMusicList().remove(searchSongById(id));
		
	}
}
