import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;

public class EncryptorThread extends Thread{
    private GUIform form;
    private File file;
    private ZipParameters parameters;
    private String password;
    public EncryptorThread(GUIform form){
        this.form = form;
    }
    public void setFile(File file){
        this.file = file;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setParameters(){
        parameters = ParametersContainer.getParameters();
    }
    @Override
    public void run() {
        form.setButtonEnabled(false);
        try{
            String archiveName = getArchiveName();
            ZipFile zipFile = new ZipFile(archiveName, password.toCharArray());
            if(file.isDirectory()){
                zipFile.addFolder(file, parameters);
            }
        } catch (Exception ex){
            form.showWarning(ex.getMessage());
        }
        form.setButtonEnabled(true);
        form.showFinished();
    }
    private String getArchiveName(){
        for(int i = 1; ; i++){
            String number = i > 1 ? Integer.toString(i) : "";
            String archiveName = file.getAbsolutePath() + number +  ".enc";
            if(!new File(archiveName).exists()){
                return archiveName;
            }
        }
    }
}
