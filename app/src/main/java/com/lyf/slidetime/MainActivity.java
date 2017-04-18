package com.lyf.slidet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lyf.slidetime.AppUtils;
import com.lyf.slidetime.MyObserver;
import com.lyf.slidetime.NetWork;
import com.lyf.slidetime.ReadView;
import com.lyf.slidetime.javabean.Book;
import com.lyf.slidetimeaxis.XLHStepView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String str ="新华社北京4月19日电  中共中央总书记、国家主席、中央军委主席、中央网络安全和信息化领导小组组长习近平19日上午在京主持召开网络安全和信息化工作座谈会并发表重要讲话，强调按照创新、协调、绿色、开放、共享的发展理念推动我国经济社会发展，是当前和今后一个时期我国发展的总要求和大趋势，我国网信事业发展要适应这个大趋势，在践行新发展理念上先行一步，推进网络强国建设，推动我国网信事业发展，让互联网更好造福国家和人民。\n" +
            "\n" +
            "中共中央政治局常委、中央网络安全和信息化领导小组副组长李克强、刘云山出席座谈会。\n" +
            "\n" +
            "习近平主持座谈会，他首先表示，我国互联网事业快速发展，网络安全和信息化工作扎实推进，取得显著进步和成绩，同时也存在不少短板和问题。召开这次座谈会，就是要当面听取大家意见和建议，共同探讨一些措施和办法，以利于我们把工作做得更好。\n" +
            "\n" +
            "座谈会上，中国工程院院士、中国电子科技集团公司总工程师吴曼青，安天实验室首席架构师肖新光，阿里巴巴集团董事局主席马云，友友天宇系统技术有限公司首席执行官姚宏宇，解放军驻京某研究所研究员杨林，北京大学新媒体研究院院长谢新洲，北京市委网信办主任佟力强，华为技术有限公司总裁任正非，国家计算机网络与信息安全管理中心主任黄澄清，复旦大学网络空间治理研究中心副主任沈逸先后发言。他们分别就实现信息化发展新跨越、加快构建信息领域核心技术体系、互联网企业的国家责任、实现网信军民融合深度发展、发挥新媒体在凝聚共识中的作用、突破信息产业发展和网络安全保障基础理论和核心技术、加强网络信息安全技术能力建设顶层设计等谈了意见和建议。\n" +
            "\n" +
            "习近平认真听取了大家的发言，并不时就有关问题同发言者深入讨论。在听取了发言后，习近平发表了重要讲话。他表示，几位同志讲得很好，分析了当前互联网发展新情况新动向，介绍了信息化发展新技术新趋势，提出了很好的意见和建议，听了很受启发。你们的发言，体现了务实的态度、创新的精神、强烈的责任感，也体现了在互联网领域较高的理论和实践水平，对我们改进工作很有帮助。有关部门要认真研究大家的意见和建议，能吸收的尽量吸收。\n" +
            "\n" +
            "习近平指出，我国有7亿网民，这是一个了不起的数字，也是一个了不起的成就。我国经济发展进入新常态，新常态要有新动力，互联网在这方面可以大有作为。要着力推动互联网和实体经济深度融合发展，以信息流带动技术流、资金流、人才流、物资流，促进资源配置优化，促进全要素生产率提升，为推动创新发展、转变经济发展方式、调整经济结构发挥积极作用。\n" +
            "\n" +
            "习近平强调，网信事业要发展，必须贯彻以人民为中心的发展思想。要适应人民期待和需求，加快信息化服务普及，降低应用成本，为老百姓提供用得上、用得起、用得好的信息服务，让亿万人民在共享互联网发展成果上有更多获得感。";

    private String str2 ="习近平指出，要建设网络良好生态，发挥网络引导舆论、反映民意的作用。实现“两个一百年”奋斗目标，需要全社会方方面面同心干，需要全国各族人民心往一处想、劲往一处使。网民来自老百姓，老百姓上了网，民意也就上了网。群众在哪儿，我们的领导干部就要到哪儿去。各级党政机关和领导干部要学会通过网络走群众路线，经常上网看看，了解群众所思所愿，收集好想法好建议，积极回应网民关切、解疑释惑。对广大网民，要多一些包容和耐心，对建设性意见要及时吸纳，对困难要及时帮助，对不了解情况的要及时宣介，对模糊认识要及时廓清，对怨气怨言要及时化解，对错误看法要及时引导和纠正，让互联网成为了解群众、贴近群众、为群众排忧解难的新途径，成为发扬人民民主、接受人民监督的新渠道。对网上那些出于善意的批评，对互联网监督，不论是对党和政府工作提的还是对领导干部个人提的，不论是和风细雨的还是忠言逆耳的，我们不仅要欢迎，而且要认真研究和吸取。\n" +
            "\n" +
            "习近平强调，网络空间是亿万民众共同的精神家园。网络空间天朗气清、生态良好，符合人民利益。网络空间乌烟瘴气、生态恶化，不符合人民利益。我们要本着对社会负责、对人民负责的态度，依法加强网络空间治理，加强网络内容建设，做强网上正面宣传，培育积极健康、向上向善的网络文化，用社会主义核心价值观和人类优秀文明成果滋养人心、滋养社会，做到正能量充沛、主旋律高昂，为广大网民特别是青少年营造一个风清气正的网络空间。\n" +
            "\n" +
            "习近平指出，要尽快在核心技术上取得突破。要有决心、恒心、重心，树立顽强拼搏、刻苦攻关的志气，坚定不移实施创新驱动发展战略，抓住基础技术、通用技术、非对称技术、前沿技术、颠覆性技术，把更多人力物力财力投向核心技术研发，集合精锐力量，作出战略性安排。我国网信领域广大企业家、专家学者、科技人员要树立这个雄心壮志。要在科研投入上集中力量办大事、积极推动核心技术成果转化，推动强强联合、协同攻关，探索组建产学研用联盟。可以探索搞揭榜挂帅，把需要的关键核心技术项目张出榜来，英雄不论出处，谁有本事谁就揭榜。新技术是人类文明发展的成果，只要有利于提高我国社会生产力水平、有利于改善人民生活，我们都不拒绝。核心技术的根源问题是基础研究问题，基础研究搞不好，应用技术就会成为无源之水、无本之木。";

    private String str3 = "习近平强调，网络安全和信息化是相辅相成的。安全是发展的前提，发展是安全的保障，安全和发展要同步推进。要树立正确的网络安全观，加快构建关键信息基础设施安全保障体系，全天候全方位感知网络安全态势，增强网络安全防御能力和威慑能力。网络安全为人民，网络安全靠人民，维护网络安全是全社会共同责任，需要政府、企业、社会组织、广大网民共同参与，共筑网络安全防线。\n" +
            "\n" +
            "习近平指出，我国互联网企业由小到大、由弱变强，在稳增长、促就业、惠民生等方面发挥了重要作用。企业搞大了、搞好了、搞到世界上去了，为国家和人民作出更大贡献了，是国家的光荣。应该鼓励和支持企业成为研发主体、创新主体、产业主体，鼓励和支持企业布局前沿技术，推动核心技术自主创新，创造和把握更多机会，参与国际竞争，拓展海外发展空间。政府要为企业发展营造良好环境，减轻企业负担，破除体制机制障碍。要加快网络立法进程，完善依法监管措施，化解网络风险。中国开放的大门不能关上，也不会关上。外国互联网企业，只要遵守我国法律法规，我们都欢迎。\n" +
            "\n" +
            "习近平强调，要聚天下英才而用之，为网信事业发展提供有力人才支撑。网络空间的竞争，归根结底是人才竞争。引进人才力度要进一步加大，人才体制机制改革步子要进一步迈开。各级党委和政府要从心底里尊重知识、尊重人才，为人才发挥聪明才智创造良好条件。要不拘一格降人才，解放思想，慧眼识才，爱才惜才。对待特殊人才要有特殊政策，不要求全责备，不要论资排辈，不要都用一把尺子衡量。要建立灵活的人才激励机制，让作出贡献的人才有成就感、获得感。要构建具有全球竞争力的人才制度体系。不管是哪个国家、哪个地区的，只要是优秀人才，都可以为我所用。\n" +
            "\n" +
            "马凯、王沪宁、刘奇葆、范长龙、孟建柱、栗战书、杨洁篪、周小川出席座谈会。\n" +
            "\n" +
            "中央网络安全和信息化领导小组成员，中央和国家机关有关部门负责同志，部分省市党委宣传部部长，各省区市网信办主任，部分中央新闻单位和中央新闻网站负责同志，有关专家学者，部分网信企业负责人等参加座谈会。";

    private ReadView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.init(this);
        setContentView(R.layout.activity_main);
        rv = (ReadView) findViewById(R.id.rv);

        NetWork.getNetService()
                .getRegister("万事如易全文阅读", 1 + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Book>() {
                    @Override
                    protected void onSuccess(Book data, String resultMsg) {
                        System.out.println("ddd");
                        if (data != null) {

                            rv.drawCurPageBitmap(data.getTitle(),data.getContent(),1);
                        }
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        System.out.println("res:"+resultMsg);
                        Toast.makeText(getApplicationContext(),"resultMsg:"+resultMsg,0).show();
                    }

                    @Override
                    public void onExit() {
                        Toast.makeText(getApplicationContext(),"resu",0).show();
                    }
                });

        rv.setmLoadPageListener(new ReadView.LoadPageListener() {
            @Override
            public void prePage(int chapter) {
                //TODO 根据书名和chapter 去数据库或者网络加载数据

                NetWork.getNetService()
                        .getRegister("万事如易全文阅读", chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Book>() {
                            @Override
                            protected void onSuccess(Book data, String resultMsg) {
                                if (data != null) {

                                    rv.drawPrePage(data.getTitle(),data.getContent());
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit() {

                            }
                        });
            }

            @Override
            public void nextPage(int chapter) {


                NetWork.getNetService()
                        .getRegister("万事如易全文阅读", chapter + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Book>() {
                            @Override
                            protected void onSuccess(Book data, String resultMsg) {
                                if (data != null) {

                                   rv.drawNextPage(data.getTitle(),data.getContent());
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit() {

                            }
                        });

            }
        });
    }
}
