package licrafter.com.v2ex.util;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.model.old.TopicResponse;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-8.
 */
public class JsoupUtil {

    public static TabContent parse(String tab, String response) {
        TabContent content = new TabContent();
        ArrayList<Topic> topics = new ArrayList<>();
        content.setName(tab);
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements elements = body.getElementsByAttributeValue("class", "cell item");
        for (Element element : elements) {
            topics.add(parseContent(element, false, null, null, tab));
        }
        content.setTopics(topics);
        //解析page totalPages;
        Elements strongs = body.getElementsByTag("strong");
        content.setPage(1);
        content.setTotalPages(1);
        if (tab.equals("recent")){
            Element titleElement = document.head().getElementsByTag("title").get(0);
            String[] array = titleElement.text().replace("V2EX › 最近的主题 ","").split("/");
            content.setPage(Integer.valueOf(array[0]));
            content.setTotalPages(Integer.valueOf(array[1]));
        }
        return content;
    }

    public static TabContent parseNodeTopics(String node, String nodeName, String response) {
        TabContent content = new TabContent();
        ArrayList<Topic> topics = new ArrayList<>();
        content.setName(node);
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements elements = body.getElementsByTag("table");
        for (Element element : elements) {
            if (element.toString().contains("item_title")) {
                Topic topic = parseContent(element, true, node, nodeName, null);
                topics.add(topic);
            }
        }
        content.setTopics(topics);
        //解析page totalPages;
        int[] array = parseNodePage(body);
        content.setPage(array[0]);
        content.setTotalPages(array[1]);
        return content;
    }

    private static Topic parseContent(Element element, boolean isParseNode, String nodeId, String nodeName, String tabName) {
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
                    String[] array = spanStr.split("•");
                    if (!isParseNode) {
                        topic.setCreateTime(array[0]);
                    } else {
                        if (array.length >= 3){
                            topic.setCreateTime(array[2]);
                        }
                        else topic.setCreateTime("");
                    }
                }
            }
        }
        if (isParseNode) {
            topic.setNodeName(nodeName);
            topic.setNodeId(nodeId);
        }
        if (!isParseNode) {
            topic.setTabName(tabName);
        }
        return topic;
    }

    private static int[] parseNodePage(Element body) {
        int[] array = new int[]{1, 1};
        Elements elements = body.getElementsByAttributeValue("class", "inner");
        for (Element el : elements) {
            Elements tds = el.getElementsByTag("td");
            if (tds.size() != 3) continue;

            String pageString = el.getElementsByAttributeValue("align", "center").text();
            pageString = pageString.split(" · ")[0];
            String[] arrayString = pageString.split("/");
            if (arrayString.length != 2) continue;
            array[1] = Integer.parseInt(arrayString[1]);
            array[0] = Integer.parseInt(arrayString[0]);
            break;
        }
        return array;
    }

    public static TopicResponse parseTopicRes(Context context, String response) {
        TopicResponse topic = new TopicResponse();
        TopicResponse.TopicDetail detail = new TopicResponse.TopicDetail();
        List<TopicResponse.Comment> comments = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element body = document.body();
        //解析帖子内容
        Elements divNodes = body.getElementsByTag("div");
        for (Element div : divNodes) {
            String divStr = div.toString();
            if (divStr.contains("class=\"topic_content\"")) {
                detail.content = divStr;
                detail.repliesCount = "0 回复";
            } else if (divStr.contains("class=\"fr\"")) {
                Elements spans = div.getElementsByTag("span");
                for (Element span : spans) {
                    String spanStr = span.toString();
                    if (spanStr.contains("class=\"gray\"")) {
                        String text = span.text();
                        detail.repliesCount = text.substring(0, text.indexOf("+"));
                    }
                }
            }
        }
        //解析点击数和发布时间
        Elements smallNodes = body.getElementsByTag("small");
        for (Element small : smallNodes) {
            String smallStr = small.toString();
            if (smallStr.contains("class=\"gray\"")) {
                String text = small.text();
                detail.createTime = text.replace("&nbsp;", "").substring(text.indexOf("at"));
            }
        }

        topic.setDetail(detail);
        //解析回复列表
        Elements tableNodes = body.getElementsByTag("table");
        for (Element table : tableNodes) {
            if (table != null && table.toString().contains("class=\"reply_content\"")) {
                comments.add(parseComments(table));
            }
        }
        topic.setComments(comments);
        return topic;
    }

    public static TopicResponse.Comment parseComments(Element table) {
        TopicResponse.Comment comment = new TopicResponse.Comment();
        Elements imgNodes = table.getElementsByTag("img");
        for (Element img : imgNodes) {
            if (img != null) {
                String imgStr = img.toString();
                if (imgStr.contains("class=\"avatar\"")) {
                    String avatar = img.attr("src");
                    if (avatar.startsWith("//")) {
                        comment.avatar = ("http:" + avatar);
                    }
                }
            }

        }
        Elements aNodes = table.getElementsByTag("a");
        for (Element a : aNodes) {
            if (a != null) {
                String aStr = a.toString();
                if (aStr.contains("/member/")) {
                    comment.userName = a.attr("href").replace("/member/", "");
                }
            }
        }
        Elements spanNodes = table.getElementsByTag("span");
        for (Element span : spanNodes) {
            if (span != null) {
                String spanStr = span.toString();
                if (spanStr.contains("class=\"fade small\"")) {
                    comment.createTime = span.text();
                } else if (spanStr.contains("class=\"no\"")) {
                    comment.rank = span.text();
                }
            }
        }

        Elements divNodes = table.getElementsByTag("div");
        for (Element div : divNodes) {
            String divStr = div.toString();
            if (divStr.contains("class=\"reply_content\"")) {
                comment.content = divStr;
            }
        }

        return comment;
    }

    public static String parseOnce(String response){
        String once = "";
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements inputNodes = body.getElementsByAttributeValue("name", "once");
        for (Element input : inputNodes){
            once = input.attr("value");
            android.util.Log.d("ljx",once);
        }
        return once;
    }

    public static LoginResult parseLoginResult(String response) {
        LoginResult loginResult=null;
        Element body = Jsoup.parse(response).body();
        Element td = body.select("div#Rightbar").select("div.box").get(0).getElementsByTag("td").get(0);
        String userId = td.getElementsByTag("a").get(0).attr("href").replace("/member/","");
        String src = "http:"+td.getElementsByTag("img").get(0).attr("src");
        android.util.Log.d("ljx","userid = "+userId+" src = "+src);
        loginResult = new LoginResult(userId,src);
        return loginResult;
    }
}
