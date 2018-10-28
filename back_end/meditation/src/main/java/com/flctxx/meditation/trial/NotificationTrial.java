package com.flctxx.meditation.trial;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.utils.NetworkUtility;

public class NotificationTrial {
	
	private static final Logger logger = Logger.getLogger(Notification.class);
    public static final String resUrlStarter = "http://39.108.226.195:8080/resources";
    public static final String urlStarter = "http://39.108.226.195:8080/meditation";
    
	public static void main(String[] args) {
		insertLesson();
		//insertMusic();
		getMusic();
	}
	
	public static void getMusic(){
		JSONObject idObject = new JSONObject();
		idObject.put("type", "music");
		String jsonString = JSON.toJSONString(idObject);
		String postUrl = urlStarter + "/getNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		for (Notification notification:resArray){
			logger.info(notification.getType());
		}
		//assertTrue(resArray.get(0).getTitle().equals("Notification"));	
	}
	
	public static void insertMusic(){
		String[] typeNames = {"rainwave", "soundeffect", "echoesofnature"};
		for(String type:typeNames){
			for (int i=1; i<=5; i++){
				Random ran = new Random(System.currentTimeMillis());
				
				Notification music = new Notification();
				music.setContent("");
				music.setId(ran.nextInt()+"");
				music.setImgUrl(resUrlStarter+"/img/"+type+"_"+i+".jpg");
				music.setResUrl(resUrlStarter+"/music/"+type+"_"+i+".mp3");
				music.setTime("");
				music.setTitle(type+" "+i);
				music.setType("music_"+type);
				
				String jsonString = JSON.toJSONString(music);
				String postUrl = urlStarter + "/updateNotification";
				String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
			}
		}
	}
	
	public static void insertLesson(){
		String[] content = {"如果您是初学者并且对冥想完全陌生并在学习如何冥想。这是初学者30天冥想挑战的第一天，将教你冥想的基本知识和如何冥想。对初学者采取这种冥想挑战并学习冥想，以便你可以改善自己的健康状况，提高注意力，降低血压，减轻压力，更清晰的思考，提高意识，变得更加精神，改善生活，可以使你更快乐，健康。   \n今天的挑战将是一个简单的呼吸冥想，教你如何冥想和如何清空你的思想和内心的烦躁。刚开始冥想时最困难的事情就是清空你的思绪......你的思绪会徘徊，你会开始与自己进行内心的交谈，这是完全正常的...所以这个练习会有所帮助你保持专注并保持静止。"
				, "计算呼吸是一种有效的平静呼吸且在冥想期间保持专注的方式。 我们将在吸气时数到5，在呼气时数到8，让您的副交感神经系统变得活跃，这样您就可以放松并减轻压力和焦虑。 每当您需要休息或感到焦虑或紧张时，您可以随时进行冥想练习。 这段视频将引导您完成这种呼吸冥想并为您计数，以便您可以专注于呼吸和放松。 这是一种很好的技术，可以在几分钟内减少恐惧和焦虑。 如果您感到头晕，请停止运动或尝试使吸气和呼气缩短。"
				, "这种全身扫描冥想将帮助你在晚上入睡，并对抗失眠和压力。 引导睡眠冥想将引导您通过身体的每个部位来释放紧张和担心，以便您可以入睡。 身体扫描是释放压力和紧张的好方法，可以帮助您放松并消除不必要的消极情绪。 所以今天对初学者的30天冥想挑战的挑战将集中在如何更好地睡眠和改善整体健康。冥想可以帮助你减轻压力，减少生气和沮丧。 因此，扫描你的身体，消除紧张和忧虑，这样你就可以消除失眠和睡眠障碍，有一个愉快的长期和平静的睡眠。"
				, "正念冥想或内观冥想，都是为了让你意识到你周围和内部的所有事物，并且非常适合缓解压力并与身体重新联系以调整自身。 正念让你专注于现在的时刻，而不是反思过去或担心未来。 你必须要注意并立即体验这一刻。 正念是一种非常着名的冥想类型，对初学者来说非常好，因为它非常简单易行。 你需要做的就是观察你的呼吸，你所听到的，你的嗅觉和感觉，但不分析任何东西或与思想交互。 你正在学习着变的专注和细心。\n对于初学者来说，这是30天冥想挑战的第4天，帮助您在这个充满压力的现代社会中找到内心的平静与幸福。"
				, "学会感恩非常有效，它使我们处于一种快乐和幸福的状态，提高了我们的振动的频率，因为我们变得更加积极并向宇宙发出爱，这影响了吸引力法则。 因此，感谢所有你拥有的东西，并用吸引力法则的力量吸引更多的美好的事物，使你的振动频率变得更高。\n\n这个冥想练习可以让你反思你生活中的所有事情，这样你就可以感激你现在所处的状态。 我们需要了解我们的目标，以便走到我们想去的地方。 因此，对于吸引力法则进行这种感恩冥想，并将幸福，成功和爱吸引到你的生活中。"
				, "接地冥想是在您的生活中创造平衡和稳定的好方法。 接地可帮助您重新连接地球，让您感觉居中和稳定。 接地冥想可以让您在生活中感受到平衡，减轻压力和焦虑。 对于患有焦虑或恐慌发作的人来说，接地尤其有益，因为他们会感到失去控制和漂浮......然后，接地冥想能够通过给予他们稳定性来减轻恐慌发作的焦虑程度和严重程度。这是初学者30天冥想挑战的第6天，这是一个简单的练习，你只需要听我的声音，因为我引导你完成这个冥想练习，以放松和缓解压力。"
				, "治愈并打开你的根脉轮，海底轮，以创造稳定和安全。 根脉轮需要愈合，打开和旋转，以在整个脉轮系统中创造平衡。 脉轮是从脊柱底部到头顶的精神能量场，每个脉轮都与不同的个体特征相关联。 根脉轮是稳定的精神位置。 一个开放和愈合的根脉轮将创造一种基础和安全的感觉，并减少焦虑和恐惧，因为你有一个坚实的基础。 根脉轮将你与地球和物质世界联系起来。 这是一种治疗冥想，让你成为一个可以成为一个人并且自信和坚强的人。这是对根脉轮的引导冥想，具有放松和柔和的环境音乐，非常适合初学者和想要学习如何冥想的人。 因此，为初学者加入这30天的冥想挑战......这是第7天。"
		};		
		String type="lesson";
		for (int i=1; i<=7; i++){
			Random ran = new Random(System.currentTimeMillis());
			
			Notification music = new Notification();
			music.setContent(content[i-1]);
			music.setId(ran.nextInt()+"");
			music.setImgUrl(resUrlStarter+"/img/"+type+"_"+i+".jpg");
			music.setResUrl(resUrlStarter+"/music/"+type+i+".mp3");
			music.setTime("");
			music.setTitle("Day"+i);//Left
			music.setType("music_"+type);
			
			String jsonString = JSON.toJSONString(music);
			String postUrl = urlStarter + "/updateNotification";
			String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		}
	}
	
	
	public static void insertArticle(){
		Notification notification = new Notification();
		notification.setId("88");
		notification.setTitle("wo");
		notification.setContent("jjjjjjjjjjjjjj");
		notification.setTime("20181024");
		notification.setType("journal_test");
		String jsonString = JSON.toJSONString(notification);
		String postUrl = urlStarter + "/updateNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject res = JSONObject.parseObject(resJsonString); 
		logger.info(resJsonString);
	}
	
  

}
