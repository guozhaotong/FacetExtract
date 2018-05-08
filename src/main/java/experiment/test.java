package experiment;


import newStep.ITranToChinese;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class test {

    public static void main(String[] args) {
        System.out.println(ITranToChinese.trans("举例"));
//        try {
//            List<String> keyanName = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\otherFiles\\Data_structure_topics.txt"), "utf-8");
//            List<String> zhenshiName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\数据结构\\数据结构主题.txt"), "utf-8");
//            ArrayList<String> keyanzhongwenName = new ArrayList<>();
//            for(int i = 0; i < zhenshiName.size(); i++){
//                zhenshiName.set(i, zhenshiName.get(i).replaceAll("\\(.*?\\)|（.*?）", ""));
//            }
//            StringBuilder stringBuilder = new StringBuilder("");
//            for(String s : keyanName){
//                Topic topic = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\9_TransToChinese\\" + s + ".txt");
//                keyanzhongwenName.add(topic.getName().replaceAll("\\(.*?\\)|（.*?）", ""));
//            }
//            HashSet<String> kName = new HashSet<>(keyanzhongwenName);
//            for(int i = 0; i < zhenshiName.size(); i++){
//                String string = zhenshiName.get(i).replaceAll("\\(.*?\\)|（.*?）", "");
//                stringBuilder.append(string);
//                if(kName.contains(string)){
//                    stringBuilder.append(" ");
//                    int j = keyanzhongwenName.indexOf(string);
//                    stringBuilder.append(keyanName.get(j));
//                }
//                stringBuilder.append("\n");
//            }
//            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\数据结构\\主题及应用中文.txt"), stringBuilder.toString(), "utf-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
