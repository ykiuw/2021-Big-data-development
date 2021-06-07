package main;

import java.nio.file.*;

public class AddandDelete {
    public static void main(String[] args)throws Exception {
        WatchService watchService= FileSystems.getDefault().newWatchService();
        Paths.get("C:/Users/86733/Desktop/zhangziyi").register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        while (true){
            WatchKey key=watchService.take();
            for(WatchEvent<?>evet:key.pollEvents()){
                System.out.println(evet.context()+"文件发生了"+evet.kind()+"事件");
                String filename = "C:/Users/86733/Desktop/zhangziyi/"+evet.context();
                if(evet.kind()==StandardWatchEventKinds.ENTRY_CREATE){
                    Add addEvent=new Add();
                    addEvent.addfile(filename);
                }
                if(evet.kind()== StandardWatchEventKinds.ENTRY_DELETE){
                    Delete deleteEvent=new Delete();
                    deleteEvent.deletefile(filename);
                }
                if(evet.kind()==StandardWatchEventKinds.ENTRY_MODIFY){
                    Add addEvent=new Add();
                    addEvent.addfile(filename);
                }
            }
            boolean vaild=key.reset();
            if(!vaild){
                break;
            }
        }
    }
}
