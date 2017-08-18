package com.roch.fupin.utils;

import java.util.ArrayList;
import java.util.List;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.roch.fupin.app.MyApplication;
import com.roch.fupin.entity.HelpObjectMenu;
import com.roch.fupin.entity.HelpSubjectMenu;
import com.roch.fupin.entity.HomeMenu;
import com.roch.fupin.entity.MapEntity;
import com.roch.fupin.entity.Menu;
import com.roch.fupin.entity.MoreMenu;
import com.roch.fupin.entity.NoPoorProjectMenu;
import com.roch.fupin.entity.NoticeMenu;
import com.roch.fupin.entity.ProjectInfoAppEntity;
import com.roch.fupin.entity.StatisticMenu;
import com.roch.fupin.entity.ViewItem;

/**
 * 自定义的保存获取的菜单
 * @author ZhaoDongShao 2016年5月13日
 */
public class DbUtil {

	static DbUtils dbUtils;

	public static void SaveMenu(List<Menu> menus) throws Exception {
		dbUtils = MyApplication.getInstance().getDbUtilsInstance(Common.LoginName);

		List<Menu> Menus = null;
		for (Menu menu : menus) {
			if (menu.getName().equals(Common.EXTS_NOTIC_NAME)) {
				// List<NoticeMenu> noticeMenus = new ArrayList<NoticeMenu>();
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				for (Menu menu2 : Menus) {
					NoticeMenu mNoticeMenu = new NoticeMenu();
					if (menu2 != null) {
						SaveHomeMenu(menu2, dbUtils);
						// 将菜单转换为json
						String menujson = GSONUtil.objectToJson(menu2);
						// 将json转换为菜单
						mNoticeMenu = (NoticeMenu) GSONUtil.fromJson(menujson, NoticeMenu.class);
					}
					// mNoticeMenu.setChecked(menu2.getChecked());
					// mNoticeMenu.setIcon(menu2.getIcon());
					// mNoticeMenu.setId(menu2.getId());
					// mNoticeMenu.setName(menu2.getName());
					// mNoticeMenu.setNavType(menu2.getNavType());
					// mNoticeMenu.setOrdernum(menu2.getOrdernum());
					// mNoticeMenu.setParent_id(menu2.getParent_id());
					// mNoticeMenu.setView_path(menu2.getView_path());
					mNoticeMenu.setIcon(URLs.IMAGE_URL + menu2.getIcon());
					// noticeMenus.add(mNoticeMenu);

					NoticeMenu noticeMenu = dbUtils.findFirst(
							Selector.from(NoticeMenu.class).where(WhereBuilder.b("mid", "=", mNoticeMenu.getMid())));
					if (noticeMenu == null) {
						dbUtils.save(mNoticeMenu);
					} else {
						dbUtils.update(mNoticeMenu, WhereBuilder.b("mid", "=", mNoticeMenu.getMid()));
					}
				}
			} else if (menu.getName().equals(Common.EXTS_HELP_SUBJECT_NAME)) {
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				for (Menu menu2 : Menus) {
					HelpSubjectMenu helpSubject = new HelpSubjectMenu();
					if (menu2 != null) {
						SaveMoreMenu(menu2, dbUtils);
						String menujson = GSONUtil.objectToJson(menu2);
						helpSubject = (HelpSubjectMenu) GSONUtil.fromJson(menujson, HelpSubjectMenu.class);
					}

					helpSubject.setIcon(URLs.IMAGE_URL + menu2.getIcon());
					HelpSubjectMenu mHelpSubject = dbUtils.findFirst(
							Selector.from(HelpSubjectMenu.class).where(WhereBuilder.b("mid", "=", helpSubject.getMid())));
					if (mHelpSubject == null) {
						dbUtils.save(helpSubject);
					} else {
						dbUtils.update(helpSubject, WhereBuilder.b("mid", "=", helpSubject.getMid()));
					}
				}
			} else if (menu.getName().equals(Common.EXTS_HELP_OBJECT_NAME)) {
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				for (Menu menu2 : Menus) {
					HelpObjectMenu helpObject = new HelpObjectMenu();
					if (menu2 != null) {
						SaveHomeMenu(menu2, dbUtils);
						String menujson = GSONUtil.objectToJson(menu2);
						helpObject = (HelpObjectMenu) GSONUtil.fromJson(menujson, HelpObjectMenu.class);
					}
					helpObject.setIcon(URLs.IMAGE_URL + menu2.getIcon());
					HelpObjectMenu mHelpObject = dbUtils.findFirst(
							Selector.from(HelpObjectMenu.class).where(WhereBuilder.b("mid", "=", helpObject.getMid())));
					if (mHelpObject == null) {
						dbUtils.save(helpObject);
					} else {
						dbUtils.update(helpObject, WhereBuilder.b("mid", "=", helpObject.getMid()));
					}
				}
			} else if (menu.getName().equals(Common.EXTS_NO_POOR_PROJECT_NAME)) {
				// 获取子菜单
				List<Menu> nopoorprojectMenu = new ArrayList<Menu>();
				nopoorprojectMenu = menu.getChildren();

				for (int j = 0; j < 3; j++) {
					SaveHomeMenu(nopoorprojectMenu.get(j), dbUtils);
				}

				for (int i = 0; i < nopoorprojectMenu.size(); i++) {
					NoPoorProjectMenu noPoorProject = new NoPoorProjectMenu();
					if (nopoorprojectMenu.get(i) != null) {
						SaveMoreMenu(nopoorprojectMenu.get(i), dbUtils);

						String menujson = GSONUtil.objectToJson(nopoorprojectMenu.get(i));
						noPoorProject = (NoPoorProjectMenu) GSONUtil.fromJson(menujson, NoPoorProjectMenu.class);
					}
					noPoorProject.setIcon(URLs.IMAGE_URL + nopoorprojectMenu.get(i).getIcon());
					NoPoorProjectMenu mNoPoorProject = dbUtils.findFirst(Selector.from(NoPoorProjectMenu.class)
							.where(WhereBuilder.b("mid", "=", noPoorProject.getMid())));
					if (mNoPoorProject == null) {
						dbUtils.save(noPoorProject);
					} else {
						dbUtils.update(noPoorProject, WhereBuilder.b("mid", "=", noPoorProject.getMid()));
					}
				}
			} else if (menu.getName().equals(Common.EXTS_STATISTIC)) { // 统计报表
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				for (int j = 0; j < 2; j++) {
					SaveHomeMenu(Menus.get(j), dbUtils);
				}
				for (int i = 0; i < Menus.size(); i++) {
					StatisticMenu statisticMenu = new StatisticMenu();
					if (Menus.get(i) != null) {
						SaveMoreMenu(Menus.get(i), dbUtils);
						String menujson = GSONUtil.objectToJson(Menus.get(i));
						statisticMenu = (StatisticMenu) GSONUtil.fromJson(menujson, StatisticMenu.class);
					}
					statisticMenu.setIcon(URLs.IMAGE_URL + Menus.get(i).getIcon());
					StatisticMenu mNoPoorProject = dbUtils.findFirst(
							Selector.from(StatisticMenu.class).where(WhereBuilder.b("mid", "=", statisticMenu.getMid())));
					if (mNoPoorProject == null) {
						dbUtils.save(statisticMenu);
					} else {
						dbUtils.update(statisticMenu, WhereBuilder.b("mid", "=", statisticMenu.getMid()));
					}
				}
			}
		}
	}

	/**
	 * 将菜单放到首页
	 * @param menu2
	 * @param dbUtils2
	 * 2016年7月7日  ZhaoDongShao
	 */
	private static void SaveHomeMenu(Menu menu, DbUtils dbUtils) throws Exception {
		HomeMenu homeMenu = new HomeMenu();
		if (menu != null) {
			String menujson = GSONUtil.objectToJson(menu);
			homeMenu = (HomeMenu) GSONUtil.fromJson(menujson, HomeMenu.class);
		}
		homeMenu.setIcon(URLs.IMAGE_URL + menu.getIcon());

		MoreMenu mMore = dbUtils
				.findFirst(Selector.from(MoreMenu.class)
				.where(WhereBuilder.b("mid", "=", homeMenu.getMid())));
		HomeMenu mHome = dbUtils
				.findFirst(Selector.from(HomeMenu.class)
				.where(WhereBuilder.b("mid", "=", homeMenu.getMid())));
		if (mMore == null && mHome == null) {
			dbUtils.save(homeMenu);
		} else if (mMore == null && mHome != null) {
			dbUtils.update(homeMenu, WhereBuilder.b("mid", "=", homeMenu.getMid()));
		} else if (mMore != null && mHome == null) {
			String home = GSONUtil.objectToJson(homeMenu);
			MoreMenu more2 = (MoreMenu) GSONUtil.fromJson(home, MoreMenu.class);
			dbUtils.update(more2, WhereBuilder.b("mid", "=", homeMenu.getMid()));
		}
	}

	/**
	 * 保存到更多菜里面
	 * @param dbUtils
	 * @param menu2
	 * 2016年5月16日  ZhaoDongShao
	 */
	private static void SaveMoreMenu(Menu menu, DbUtils dbUtils) throws Exception {
		MoreMenu more = new MoreMenu();
		if (menu != null) {
			String menujson = GSONUtil.objectToJson(menu);
			more = (MoreMenu) GSONUtil.fromJson(menujson, MoreMenu.class);
		}
		// more.setChecked(menu.getChecked());
		// more.setIcon(menu.getIcon());
		// more.setId(menu.getId());
		// more.setName(menu.getName());
		// more.setNavType(menu.getNavType());
		// more.setOrdernum(menu.getOrdernum());
		// more.setParent_id(menu.getParent_id());
		// more.setView_path(menu.getView_path());
		more.setIcon(URLs.IMAGE_URL + menu.getIcon());

		MoreMenu mMore = dbUtils
				.findFirst(Selector.from(MoreMenu.class)
				.where(WhereBuilder.b("mid", "=", more.getMid())));
		HomeMenu mHome = dbUtils
				.findFirst(Selector.from(HomeMenu.class)
				.where(WhereBuilder.b("mid", "=", more.getMid())));
		if (mMore == null && mHome == null) {
			dbUtils.save(more);
		} else if (mMore == null && mHome != null) {
			String home = GSONUtil.objectToJson(more);
			HomeMenu homeMenu = (HomeMenu) GSONUtil.fromJson(home, HomeMenu.class);
			dbUtils.update(homeMenu, WhereBuilder.b("mid", "=", more.getMid()));
		} else if (mMore != null && mHome == null) {
			String home = GSONUtil.objectToJson(more);
			MoreMenu more2 = (MoreMenu) GSONUtil.fromJson(home, MoreMenu.class);
			dbUtils.update(more2, WhereBuilder.b("mid", "=", more.getMid()));
		}
	}

	/**
	 * 删除不需要的菜单
	 * 2016年8月21日
	 * ZhaoDongShao
	 */
	public static void deleteMenu(List<Menu> menus) throws Exception {
		dbUtils = MyApplication.getInstance().getDbUtilsInstance(Common.LoginName);
		List<Menu> Menus = null;
		for (Menu menu : menus) {
			if (menu.getName().equals(Common.EXTS_NOTIC_NAME)) {
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				if (StringUtil.isNotEmpty(Menus)) {
					List<NoticeMenu> noticeMenus = dbUtils.findAll(Selector.from(NoticeMenu.class));
					if (StringUtil.isNotEmpty(noticeMenus)) {
						for (int i = 0; i < noticeMenus.size(); i++) {
							for (int j = 0; j < Menus.size(); j++) {
								if (noticeMenus.get(i).mid.equals(Menus.get(j).mid)) {
									noticeMenus.remove(i);
								}
							}
						}
						if (noticeMenus.size() > 0) {
							for (int i = 0; i < noticeMenus.size(); i++) {
								String json = GSONUtil.objectToJson(noticeMenus.get(i));
								deleteHome(json);
								deleteMore(json);
							}
							dbUtils.deleteAll(noticeMenus);
						}
					}
				}
			} else if (menu.getName().equals(Common.EXTS_HELP_SUBJECT_NAME)) {
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				if (StringUtil.isNotEmpty(Menus)) {
					List<HelpSubjectMenu> helpSubjectMenus = dbUtils.findAll(Selector.from(HelpSubjectMenu.class));
					if (StringUtil.isNotEmpty(helpSubjectMenus)) {
						for (int i = 0; i < helpSubjectMenus.size(); i++) {
							for (int j = 0; j < Menus.size(); j++) {
								if (helpSubjectMenus.get(i).mid.equals(Menus.get(j).mid)) {
									helpSubjectMenus.remove(i);
								}
							}
						}
						if (helpSubjectMenus.size() > 0) {
							for (int i = 0; i < helpSubjectMenus.size(); i++) {
								String json = GSONUtil.objectToJson(helpSubjectMenus.get(i));
								deleteHome(json);
								deleteMore(json);
							}
							dbUtils.deleteAll(helpSubjectMenus);
						}
					}
				}
			} else if (menu.getName().equals(Common.EXTS_HELP_OBJECT_NAME)) {
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				if (StringUtil.isNotEmpty(Menus)) {
					List<HelpObjectMenu> helpObjectMenus = dbUtils.findAll(Selector.from(HelpObjectMenu.class));
					if (StringUtil.isNotEmpty(helpObjectMenus)) {
						for (int i = 0; i < helpObjectMenus.size(); i++) {
							for (int j = 0; j < Menus.size(); j++) {
								if (helpObjectMenus.get(i).mid.equals(Menus.get(j).mid)) {
									helpObjectMenus.remove(i);
								}
							}
						}
						if (helpObjectMenus.size() > 0) {
							for (int i = 0; i < helpObjectMenus.size(); i++) {
								String json = GSONUtil.objectToJson(helpObjectMenus.get(i));
								deleteHome(json);
								deleteMore(json);
							}
							dbUtils.deleteAll(helpObjectMenus);
						}
					}
				}
			} else if (menu.getName().equals(Common.EXTS_NO_POOR_PROJECT_NAME)) {
				// 获取子菜单
				List<Menu> nopoorprojectMenu = new ArrayList<Menu>();
				nopoorprojectMenu = menu.getChildren();
				if (StringUtil.isNotEmpty(Menus)) {
					List<NoPoorProjectMenu> noPoorProjectMenus = dbUtils
							.findAll(Selector.from(NoPoorProjectMenu.class));
					if (StringUtil.isNotEmpty(noPoorProjectMenus)) {
						for (int i = 0; i < noPoorProjectMenus.size(); i++) {
							for (int j = 0; j < nopoorprojectMenu.size(); j++) {
								if (noPoorProjectMenus.get(i).mid.equals(nopoorprojectMenu.get(j).mid)) {
									noPoorProjectMenus.remove(i);
								}
							}
						}
						if (noPoorProjectMenus.size() > 0) {
							for (int i = 0; i < noPoorProjectMenus.size(); i++) {
								String json = GSONUtil.objectToJson(noPoorProjectMenus.get(i));
								deleteHome(json);
								deleteMore(json);
							}
							dbUtils.deleteAll(noPoorProjectMenus);
						}
					}
				}
			} else if (menu.getName().equals(Common.EXTS_STATISTIC)) { // 统计报表
				// 获取子菜单
				Menus = new ArrayList<Menu>();
				Menus = menu.getChildren();
				if (StringUtil.isNotEmpty(Menus)) {
					List<StatisticMenu> statisticMenus = dbUtils.findAll(Selector.from(StatisticMenu.class));
					if (StringUtil.isNotEmpty(statisticMenus)) {
						for (int i = 0; i < statisticMenus.size(); i++) {
							for (int j = 0; j < Menus.size(); j++) {
								if (statisticMenus.get(i).mid.equals(Menus.get(j).mid)) {
									statisticMenus.remove(i);
								}
							}
						}
						if (statisticMenus.size() > 0) {
							for (int i = 0; i < statisticMenus.size(); i++) {
								String json = GSONUtil.objectToJson(statisticMenus.get(i));
								deleteHome(json);
								deleteMore(json);
							}
							dbUtils.deleteAll(statisticMenus);
						}
					}
				}
			}
		}
	}

	/**
	 * 删除更多里面不需要的的菜单
	 * 2016年8月21日
	 * ZhaoDongShao
	 * @param json
	 */
	private static void deleteMore(String json) throws Exception {
		if (StringUtil.isNotEmpty(json)) {
			MoreMenu menu = (MoreMenu) GSONUtil.fromJson(json, MoreMenu.class);
			if (StringUtil.isNotEmpty(menu)) {
				dbUtils.delete(menu);
			}
		}
	}

	/**
	 * 删除首页中的不需要的菜单
	 * 2016年8月21日
	 * ZhaoDongShao
	 * @param json
	 */
	private static void deleteHome(String json) throws Exception {
		if (StringUtil.isNotEmpty(json)) {
			HomeMenu menu = (HomeMenu) GSONUtil.fromJson(json, HomeMenu.class);
			if (StringUtil.isNotEmpty(menu)) {
				dbUtils.delete(menu);
			}
		}
	}

	/**
	 * 将数据转换保存到map里面
	 * @param mApp
	 * @return
	 * 2016年6月1日  ZhaoDongShao
	 */
	public static List<ViewItem> getMapList(ProjectInfoAppEntity mAppEntity) {
		List<ViewItem> lItems = new ArrayList<ViewItem>();
		lItems.add(new ViewItem(0, new MapEntity("项目", "")));
		lItems.add(new ViewItem(1, new MapEntity("项目类型", mAppEntity.getProjectcategoryidcall())));
		lItems.add(new ViewItem(1, new MapEntity("项目名称", mAppEntity.getProjectname())));
		lItems.add(new ViewItem(2, new MapEntity("建设内容", mAppEntity.getBuildcontent())));
		lItems.add(new ViewItem(1, new MapEntity("扶贫对象", mAppEntity.getProjectcategoryidcall())));
		lItems.add(new ViewItem(1, new MapEntity("项目效益", mAppEntity.getProjecteffect())));
		lItems.add(new ViewItem(1, new MapEntity("项目责任单位", mAppEntity.getDutydeptname())));
		lItems.add(new ViewItem(1, new MapEntity("项目责任人", mAppEntity.getDutyperson())));
		lItems.add(new ViewItem(1, new MapEntity("联系方式", mAppEntity.getDutypersonphone())));
		lItems.add(new ViewItem(1, new MapEntity("项目进度", mAppEntity.getProjectscheduleidcall())));
		lItems.add(new ViewItem(1, new MapEntity("项目状态", mAppEntity.getProjectstatusidcall())));
		lItems.add(new ViewItem(1, new MapEntity("中标单位名称", mAppEntity.getZhongbiaocompany())));
		lItems.add(new ViewItem(0, new MapEntity("项目时间", "")));
		lItems.add(new ViewItem(1, new MapEntity("计划开始时间", CommonUtil.getSpliteDate(mAppEntity.getPlanstartdate()))));
		lItems.add(new ViewItem(1, new MapEntity("计划结束时间", CommonUtil.getSpliteDate(mAppEntity.getPlanenddate()))));
		lItems.add(new ViewItem(1, new MapEntity("立项日期", CommonUtil.getSpliteDate(mAppEntity.getLixiangdate()))));
		lItems.add(new ViewItem(1, new MapEntity("报备日期", CommonUtil.getSpliteDate(mAppEntity.getBaobeidate()))));
		lItems.add(new ViewItem(1, new MapEntity("招标日期", CommonUtil.getSpliteDate(mAppEntity.getZhaobiaodate()))));
		lItems.add(new ViewItem(1, new MapEntity("开工日期", CommonUtil.getSpliteDate(mAppEntity.getKaigongdate()))));
		lItems.add(new ViewItem(1, new MapEntity("竣工日期", CommonUtil.getSpliteDate(mAppEntity.getJungongdate()))));
		lItems.add(new ViewItem(1, new MapEntity("验收日期", CommonUtil.getSpliteDate(mAppEntity.getYanshoudate()))));
		lItems.add(new ViewItem(0, new MapEntity("项目资金", "")));
		lItems.add(new ViewItem(1, new MapEntity("中省资金", String.valueOf(mAppEntity.getZszj()) + "万元")));
		lItems.add(new ViewItem(1, new MapEntity("市级资金", String.valueOf(mAppEntity.getSjzj()) + "万元")));
		lItems.add(new ViewItem(1, new MapEntity("镇村配套", String.valueOf(mAppEntity.getZcpt()) + "万元")));
		lItems.add(new ViewItem(1, new MapEntity("群众自筹", String.valueOf(mAppEntity.getQzzc()) + "万元")));

		lItems.add(new ViewItem(1, new MapEntity("已完成投资", String.valueOf(mAppEntity.getYwctz()) + "万元")));
		lItems.add(new ViewItem(1, new MapEntity("投资比例",
				String.valueOf(CommonUtil.getBili(mAppEntity.getYwctz(), mAppEntity.getInvesttotal())))));

		lItems.add(new ViewItem(1, new MapEntity("投资合计", String.valueOf(mAppEntity.getInvesttotal()) + "万元")));
		return lItems;
	}
}
