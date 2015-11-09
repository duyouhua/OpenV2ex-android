package licrafter.com.v2ex.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.TableContent;

/**
 * Created by shell on 15-11-8.
 */
public class JsoupUtil {

    public static TableContent parse(String response) {
        TableContent content = new TableContent();
        List<TableContent.Topic> topics = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements elements = body.getElementsByAttributeValue("class", "cell item");
        for (Element element : elements) {
            topics.add(parseContent(element));
        }
        content.setTopics(topics);
        content.setPage(1);
        content.setTotalPages(1);
        return content;
    }

    private static TableContent.Topic parseContent(Element element) {
        TableContent.Topic topic = new TableContent.Topic();
        //解析出所有的td标签
        Elements tdNodes = element.getElementsByTag("td");
        for (Element tdNode : tdNodes) {
            String tdStr = tdNode.toString();
            //td标签中有发布者头像链接和userId;
            if (tdStr.contains("class=\"avatar\"")) {
                Elements userIdNodes = tdNode.getElementsByTag("a");
                if (userIdNodes != null) {
                    String userId = userIdNodes.attr("href");
                    topic.userId = userId.replace("/member/", "");
                }
                Elements avatarNodes = tdNode.getElementsByTag("img");
                if (avatarNodes != null) {
                    String avatar = avatarNodes.attr("src");
                    if (avatar.startsWith("//")) {
                        topic.avatar = ("http:" + avatar);
                    }
                }
            } else if (tdStr.contains("class=\"item_title\"")) {
                Elements aNodes = tdNode.getElementsByTag("a");
                for (Element anode : aNodes) {
                    if (anode.toString().contains("reply")) {
                        //话题标题,id,回复数
                        topic.title = anode.text();
                        String[] array = anode.attr("href").split("#");
                        topic.topicId = array[0].replace("/t/", "");
                        topic.replies = Integer.parseInt(array[1].replace("reply", ""));
                    } else if (anode.toString().contains("class=\"node\"")) {
                        //节点名字，ｉｄ
                        topic.nodeId = anode.attr("href").replace("/go/", "");
                        topic.nodeName = anode.text();
                    }
                }
                if (tdStr.contains("最后回复")) {
                    topic.lastedReviewer = aNodes.get(aNodes.size() - 1).attr("href").replace("/member/", "");
                } else {
                    topic.lastedReviewer = "";
                }
                Elements spanNodes = tdNode.getElementsByTag("span");
                for (Element spanNode : spanNodes) {
                    String spanStr = spanNode.text();
                    String[] array = spanStr.split("  •  ");
                    topic.createTime = array[0];
                }
            }
        }
        return topic;
    }
}
