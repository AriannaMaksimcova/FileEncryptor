import net.lingala.zip4j.ZipFile;

import java.io.File;

public class DecryptorThread extends Thread{
    private GUIform form;
    private File file;
    private String password;
    public DecryptorThread(GUIform form){
        this.form = form;
    }
    @Override
    public void run() {
        form.setButtonEnabled(false);

        try{
            String outPath = getOutputPath();
            ZipFile zipFile = new ZipFile(file, password.toCharArray());
            zipFile.extractAll(outPath);
        }catch (Exception ex){
            form.showWarning(ex.getMessage());
        }
        form.setButtonEnabled(true);
        form.showFinished();
    }
    public void setFile(File file){
        this.file = file;
    }
    public void setPassword(String password){
        this.password = password;
    }
    private String getOutputPath(){
        String path = file.getAbsolutePath().replaceAll("\\.enc$", "");
        for(int i = 1; ; i++){
            String number = i > 1 ? Integer.toString(i) : "";
            String outPath = path + number;
            if(!new File(outPath).exists()){
                return path;
            }
        }
    }
}
