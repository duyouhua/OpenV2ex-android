package licrafter.com.v2ex.ui.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-8.
 */
public class JsoupUtil {

    public static TabContent parse(String tab, String response) {
        TabContent content = new TabContent();
        List<Topic> topics = new ArrayList<>();
        content.setName(tab);
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements elements = body.getElementsByAttributeValue("class", "cell item");
        for (Element element : elements) {
            topics.add(parseContent(element));
        }
        content.setTopics(topics);
        //解析page totalPages;
        Elements strongs = body.getElementsByTag("strong");
        content.setPage(1);
        content.setTotalPages(1);
        for (Element element : strongs) {
            String str = element.toString();
            if (str.contains("class=\"fade\"")) {
                android.util.Log.d("ljx", "page:" + element.text());
                String[] array = element.text().split("/");
                content.setPage(Integer.parseInt(array[0]));
                content.setTotalPages(Integer.parseInt(array[1]));
            }
        }
        return content;
    }

    private static Topic parseContent(Element element) {
        Topic topic = new Topic();
        //解析出所有的td标签
        Elements tdNodes = element.getElementsByTag("td");
        for (Element tdNode : tdNodes) {
            String tdStr = tdNode.toString();
            //td标签中有发布者头像链接和userId;
            if (tdStr.contains("class=\"avatar\"")) {
                Elements userIdNodes = tdNode.getElementsByTag("a");
                if (userIdNodes != null) {
                    String userId = userIdNodes.attr("href");
                    topic.setUserId(userId.replace("/member/", ""));
                }
                Elements avatarNodes = tdNode.getElementsByTag("img");
                if (avatarNodes != null) {
                    String avatar = avatarNodes.attr("src");
                    if (avatar.startsWith("//")) {
                        topic.setAvatar("http:" + avatar);
                    }
                }
            } else if (tdStr.contains("class=\"item_title\"")) {
                Elements aNodes = tdNode.getElementsByTag("a");
                for (Element anode : aNodes) {
                    if (anode.toString().contains("reply")) {
                        //话题标题,id,回复数
                        topic.setTitle(anode.text());
                        String[] array = anode.attr("href").split("#");
                        topic.setTopicId(array[0].replace("/t/", ""));
                        topic.setReplies(Integer.parseInt(array[1].replace("reply", "")));
                    } else if (anode.toString().contains("class=\"node\"")) {
                        //节点名字，ｉｄ
                        topic.setNodeId(anode.attr("href").replace("/go/", ""));
                        topic.setNodeName(anode.text());
                    }
                }
                if (tdStr.contains("最后回复")) {
                    topic.setLastedReviewer(aNodes.get(aNodes.size() - 1).attr("href").replace("/member/", ""));
                } else {
                    topic.setLastedReviewer("");
                }
                Elements spanNodes = tdNode.getElementsByTag("span");
                for (Element spanNode : spanNodes) {
                    String spanStr = spanNode.text();
                    String[] array = spanStr.split("  •  ");
                    topic.setCreateTime(array[0]);
                }
            }
        }
        return topic;
    }
}
