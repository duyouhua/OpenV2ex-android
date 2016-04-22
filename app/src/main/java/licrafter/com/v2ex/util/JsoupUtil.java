package licrafter.com.v2ex.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Pattern;

import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.model.ProfitTopic;
import licrafter.com.v2ex.model.TopicComment;
import licrafter.com.v2ex.model.TopicDetail;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.model.response.CreateTopicResponse;
import licrafter.com.v2ex.model.response.LoginFormInfo;
import licrafter.com.v2ex.model.response.RegFormInfo;
import licrafter.com.v2ex.util.network.ApiErrorUtil;

/**
 * Created by shell on 15-11-8.
 */
public class JsoupUtil {

    public static TabContent parseTabContent(String tab, String response) {
        TabContent content = new TabContent();
        ArrayList<Topic> topics = new ArrayList<>();
        content.setName(tab);
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements elements = body.select("div.cell").select("div.item");
        for (Element element : elements) {
            topics.add(parseContent(element, false, null, null, tab));
        }
        content.setTopics(topics);
        //解析page totalPages;
        Elements strongs = body.getElementsByTag("strong");
        content.setPage(1);
        content.setTotalPages(1);
        if (tab.equals("recent")) {
            Element titleElement = document.head().getElementsByTag("title").get(0);
            String[] array = titleElement.text().replace("V2EX › 最近的主题 ", "").split("/");
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
                Element spanNode = element.select("span.small").select("span.fade").get(0);
                String spanStr = spanNode.text();
                String[] array = spanStr.split("•");
                if (!isParseNode) {
                    if (array.length >= 3) {
                        topic.setCreateTime(array[2]);
                    } else {
                        topic.setCreateTime("");
                    }
                } else {
                    if (array.length >= 3) {
                        topic.setCreateTime(array[2]);
                    } else topic.setCreateTime("");
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

    public static TopicDetail parseTopicDetail(String response) {
        TopicDetail detail = new TopicDetail();
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Element small = body.select("small.gray").first();
        String[] arry = small.text().split(" · ");
        detail.setCreateTime(arry[1]);
        detail.setClickCount(arry[2]);
        Elements contents = body.select("div.topic_content");
        if (contents.size() > 0) {
            String content = contents.first().toString().replaceAll("max-width", "");
            parseImg(detail, contents.first(), content);
        } else {
            detail.setContent("");
        }
        Elements tokenEs = document.getElementsByTag("script");
        for (Element element : tokenEs) {
            if (element.toString().contains("csrfToken ")) {
                String script = element.toString();
                int end = script.lastIndexOf(";");
                String token = script.substring(end - 33, end - 1);
                if (token.contains("csrfToken")) {
                    detail.setCsrfToken("false");
                } else {
                    detail.setCsrfToken(token);
                }
            }
        }

        Element favoriteE = body.select("div.topic_buttons").first();
        if (favoriteE != null) {
            if (favoriteE.toString().contains("加入收藏")) {
                detail.setFravorite(false);
            } else if (favoriteE.toString().contains("取消收藏")) {
                detail.setFravorite(true);
            }
        }
        return detail;
    }

    private static void parseImg(TopicDetail detail, Element element, String content) {
        Elements imgEs = element.getElementsByTag("img");
        ArrayList<String> urls = new ArrayList<>();
        for (Element img : imgEs) {
            String imgStr = img.toString();
            String src = img.attr("src");
            String newImgStr = "<a href=\"" + src + "\">" + imgStr + "</a>";
            content = content.replace(imgStr, newImgStr);
            urls.add(src);
        }
        detail.setContent(content);
        detail.setImgUrls(urls);
    }

    public static String parseCsrfToken(String response) {
        String csrf = "false";
        Document document = Jsoup.parse(response);
        Elements tokenEs = document.getElementsByTag("script");
        for (Element element : tokenEs) {
            if (element.toString().contains("csrfToken ")) {
                String script = element.toString();
                int end = script.lastIndexOf(";");
                String token = script.substring(end - 33, end - 1);
                if (token.contains("csrfToken")) {
                    csrf = "false";
                } else {
                    csrf = token;
                }
            }
        }
        return csrf;
    }

    public static CreateTopicResponse parseNewTopicResponse(String response) {
        CreateTopicResponse resp = new CreateTopicResponse();
        Document document = Jsoup.parse(response);
        Element link = document.getElementsByAttributeValue("rel", "canonical").first();
        if (link != null) {
            resp.setUrl(link.attr("href"));
        }
        resp.setMessage(ApiErrorUtil.getErrorMsg(response));
        return resp;
    }

    public static TopicComment parseComments(String response) {
        TopicComment topicComment = new TopicComment();
        Element body = Jsoup.parse(response).body();
        Elements tableNodes = body.getElementsByTag("table");
        for (Element table : tableNodes) {
            if (table != null && table.toString().contains("class=\"reply_content\"")) {
                topicComment.getComments().add((parseComment(table)));
            }
        }
        Elements pageinput = body.select("input.page_input");
        if (pageinput.size() > 0) {
            int totalPage = Integer.valueOf(pageinput.get(0).attr("max"));
            int page = Integer.valueOf(pageinput.get(0).attr("value"));
            topicComment.setPage(page);
            topicComment.setTotalPage(totalPage);
        } else {
            topicComment.setPage(1);
            topicComment.setTotalPage(1);
        }

        return topicComment;
    }

    private static TopicComment.Comment parseComment(Element table) {
        TopicComment.Comment comment = new TopicComment.Comment();
        Element avatarImg = table.select("img.avatar").first();
        String avatar = avatarImg.attr("src");
        if (avatar.startsWith("//")) {
            comment.avatar = ("http:" + avatar);
        }

        Element userNameE = table.select("a.dark").first();
        comment.userName = userNameE.attr("href").replace("/member/", "");

        Element createTimeE = table.select("span.fade").select("span.small").first();
        comment.createTime = createTimeE.text();

        Element rankE = table.select("span.no").first();
        comment.rank = rankE.text();

        Element contentE = table.select("div.reply_content").first();
        comment.content = contentE.toString();

        return comment;
    }

    public static String parseOnce(String response) {
        String once = "";
        Document document = Jsoup.parse(response);
        Element body = document.body();
        Elements inputNodes = body.getElementsByAttributeValue("name", "once");
        for (Element input : inputNodes) {
            once = input.attr("value");
            android.util.Log.d("ljx", once);
        }
        return once;
    }

    public static LoginResult parseLoginResult(String response) {
        LoginResult loginResult = null;
        Element body = Jsoup.parse(response).body();
        Element td = body.select("div#Rightbar").select("div.box").get(0).getElementsByTag("td").get(0);
        String userId = td.getElementsByTag("a").get(0).attr("href").replace("/member/", "");
        String src = "http:" + td.getElementsByTag("img").get(0).attr("src");
        android.util.Log.d("ljx", "userid = " + userId + " src = " + src);
        loginResult = new LoginResult(userId, src);
        return loginResult;
    }

    public static ArrayList<ProfitTopic> parseProfitTopic(String response) {
        Element body = Jsoup.parse(response).body();
        Elements divs = body.select("div.cell").select("div.item");
        ArrayList<ProfitTopic> topics = new ArrayList<>();
        for (Element div : divs) {
            ProfitTopic topic = new ProfitTopic();
            topic.setTitle(div.select("span.item_title").first().text());
            topic.setOtherInfo(div.select("span.small").select("span.fade").first().text());
            topics.add(topic);
        }
        return topics;
    }

    public static RegFormInfo parseRegFormInfo(String response) {
        RegFormInfo formInfo = new RegFormInfo();
        Element body = Jsoup.parse(response).body();
        Element once = body.select("input[name=once]").first();
        formInfo.setOnce(once.attr("value"));

        Element form = body.select("form[method=post]").first();
        Elements inputs = form.getElementsByTag("input");
        formInfo.setNameKey(inputs.get(0).attr("name"));
        formInfo.setPswKey(inputs.get(1).attr("name"));
        formInfo.setEmailKey(inputs.get(2).attr("name"));
        formInfo.setCodeKey(inputs.get(3).attr("name"));
        return formInfo;
    }

    public static LoginFormInfo parseLoginFormInfo(String response){
        LoginFormInfo formInfo = new LoginFormInfo();
        Element body = Jsoup.parse(response).body();
        Element once = body.select("input[name=once]").first();
        formInfo.setOnce(once.attr("value"));

        Element form = body.select("form[method=post]").first();
        Elements inputs = form.getElementsByTag("input");
        formInfo.setNameKey(inputs.get(0).attr("name"));
        formInfo.setPswKey(inputs.get(1).attr("name"));
        return formInfo;
    }
}
