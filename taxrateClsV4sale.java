
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * encode ：：utf8 auto；；attilax v6 317 fix fmt prblm v5 可读性增强 增加注释 v4增加可读性
 * 
 * 
 */
/*
 * 计算税率主要思路流程：： 1. 内存数据表LIST 进行投影运算 循环 获取税率，UDF计算单项税金， 2. 聚合运算 计算总税 2. 格式化展示
 * 
 */
@SuppressWarnings("all")
public class taxrateClsV4sale {

	// 第一个范例
	@Test
	public void testInput1() {
		System.out.println("----------------第一个测试数据----------------");

		// System.out.println(StringUtils.rightPad("abc", 5) + "after");

		Map input1 = new HashMap() {
			{
				put("loc地点", "CA");
				put("shoplist物品列表", new ArrayList<Map>() {
					{
						add(new HashMap() {
							{
								put("item物品名", "book");
								put("qty数量", 1000);
								put("price价格", 17.99);
							}
						});
						add(new HashMap() {
							{
								put("item物品名", "potato chips");
								put("qty数量", 1);
								put("price价格", 3.99);
							}
						});
					}
				});
			}
		};
		Map rzt = calcProcsss(input1);
		formatShow(rzt);
		System.out.println("--------------end------------------");

		// calcTax(input1);
//        //    System.out.println(gettype("potato chips"));//
//
//    System.out.println(roundUP("0.16"));
//        System.out.println(roundUP("5.16"));
	}

	// 第2个范例
	@Test
	public void testInput2() {

		System.out.println("----------------第2个测试数据----------------");
		Map input1 = new LinkedHashMap() {
			{
				put("loc地点", "NY");
				put("shoplist物品列表", new ArrayList<Map>() {
					{
						add(new HashMap() {
							{
								put("item物品名", "book");
								put("qty数量", 1);
								put("price价格", 17.99);
							}
						});
						add(new HashMap() {
							{
								put("item物品名", "pencil");
								put("qty数量", 3);
								put("price价格", 2.99);
							}
						});
					}
				});
			}
		};
		Map rzt = calcProcsss(input1);
		formatShow(rzt);
//        System.out.println(rzt);
//        System.out.println(JSON.toJSONString(rzt,true));
		System.out.println("----------------  end----------------");
	}

	// 第3个范例
	@Test
	public void testInput3() {
		System.out.println("----------------第3个测试数据----------------");

		Map input1 = new LinkedHashMap() {
			{
				put("loc地点", "NY");
				put("shoplist物品列表", new ArrayList<Map>() {
					{

						add(new HashMap() {
							{
								put("item物品名", "pencil");
								put("qty数量", 2);
								put("price价格", 2.99);
							}
						});
						add(new HashMap() {
							{
								put("item物品名", "shirt");
								put("qty数量", 1);
								put("price价格", 29.99);
							}
						});
					}
				});
			}
		};

		// System.out.println(JSON.toJSONString(input1,true));
		// System.out.println(input1);
		Map rzt = calcProcsss(input1);
		formatShow(rzt);
		System.out.println("---------------- - end---------------");

	}

	// 内存数据表LIST 进行投影运算 循环 获取税率，UDF计算单项税金，并聚合运算 计算总税
	public static Map calcProcsss(Map input1) {

		((List) input1.get("shoplist物品列表")).forEach(new Consumer<Map>() {
			@Override
			public void accept(Map item) {
				Object itemtype = gettype(item.get("item物品名"));
				Map item_taxrate = selectTaxrateFrom_taxRateTable_where_loc_and_itemtype(input1.get("loc地点"), itemtype);
				item.put("itemtype物品类型", itemtype);
				item.put("taxrate税率", item_taxrate.get("tax_rate_num税率数字格式"));
				item.put("taxrate税率文本格式", item_taxrate.get("tax rate税率"));
				item.put("item_tax物品税", get_item_tax((Integer) item.get("qty数量"), (double) item.get("price价格"),
						item_taxrate.get("tax_rate_num税率数字格式")));
				item.put("item_total物品价格不含税",
						(double) item.get("price价格") * Double.parseDouble(item.get("qty数量").toString()));
				// item.put("taxrate_numFormat", Float.parseFloat(item_taxrate) );
			}
		});

		// 内存数据表聚合运算 物品总价不含税
		// select sum(物品价格不含税) from 物品列表
		DecimalFormat df2 = new DecimalFormat("##0.00");// 这样为保持2位
		double subtotal = ((List<Map>) input1.get("shoplist物品列表")).stream()
				.mapToDouble(i -> (double) i.get("item_total物品价格不含税")).sum();
		input1.put("subtotal物品总价不含税", (df2.format(subtotal + 0)));

		// 内存数据表聚合运算 计算总税
		// select sum（物品税） from 物品列表
		double all_sale_tax = ((List<Map>) input1.get("shoplist物品列表")).stream()
				.mapToDouble(i -> (double) i.get("item_tax物品税")).sum();

		input1.put("tax税", roundUP(df2.format(all_sale_tax)));

		// 计算total总价含税
		double total = Double.parseDouble(input1.get("tax税").toString())
				+ Double.parseDouble(input1.get("subtotal物品总价不含税").toString());
		input1.put("total总价含税", df2.format(total));

		return input1;

	}

	// 格式化计算税费
	private static Object roundUP(String format) {
		String price_pre = format.substring(0, format.length() - 1);
		int lastNum = Integer.parseInt(format.substring(format.length() - 1));
		if (lastNum > 0 && lastNum < 5)
			return price_pre + "5";
		if (lastNum >= 5 && lastNum <= 9) {
			Double d = Double.parseDouble(price_pre) + 0.10d;
			DecimalFormat df2 = new DecimalFormat("##0.00");// 这样为保持2位
			return df2.format(d);
		}

		return format; // 0 5
	}

	static int spanWitdh = 20;

	// 格式化展示
	private static void formatShow(Map input1) {
		String tabs = "\t\t\t\t";
		int col_len = 22;

		// title is rit pad
		System.out.println(rightPad("item") + rightPad("price") + "qty");
		int priceLastIndex = spanWitdh + "price".length();
		int qtyEndIndex = spanWitdh * 2 + "qty".length();
		int qtyLen = qtyEndIndex - priceLastIndex;
		String title = "item,price,qty";

		((List) input1.get("shoplist物品列表")).forEach(new Consumer<Map>() {

			@Override
			public void accept(Map map) {

				String leftPadQty = StringUtils.leftPad(map.get("qty数量").toString(), qtyLen);
				// System.out.println(leftPadQty);
				if (map.get("item物品名").toString().equals("potato chips"))
					System.out.println("Dbg");
				System.out.println((map.get("item物品名")) + rightAlign("price价格", baseTitle("price"), map, title)
						+ rightAlign("qty数量", baseTitle("qty"), map, title));
			}

		});

		// int lastPos = 2 * spanWitdh + "qty".length();
		//
		System.out.println("subtotal:" + rightAlign(input1.get("subtotal物品总价不含税"), "qty", 2, "subtotal:".length()));
		System.out.println("tax:" + rightAlign(input1.get("tax税"), "qty", 2, "tax:".length()));

		System.out.println("total:" + rightAlign(input1.get("total总价含税"), "qty", 2, "total:".length()));

	}

	protected static String rightAlign(String item, String alignbaseTitle, Map map, String title) {
		String[] strArray = title.split(",");
		Map m = new HashMap();
		for (int i = 0; i < strArray.length; i++) {
			m.put(strArray[i], i);
		}
		// List list = Arrays.asList(strArray);
		int alignbaseTitleIdx = (int) m.get(alignbaseTitle);

		int lastPos = alignbaseTitleIdx * spanWitdh + alignbaseTitle.length();
		int preItemIdx = alignbaseTitleIdx - 1;
		String preItemMapKeyname = strArray[preItemIdx];

		int preItemEndPos = preItemIdx * spanWitdh + map.get(preItemMapKeyname).toString().length();
		if (preItemMapKeyname == "price") {
			// pre item is rit align
			preItemEndPos = preItemIdx * spanWitdh + preItemMapKeyname.length();
		}
		return leftpadPrice(preItemEndPos, lastPos, item, map);

	}

	// preitem is rightAlign too
	protected static Object rightAlign(String item, String alignbaseTitle, int alignbaseTitleIdx, Map map) {
		int lastPos = alignbaseTitleIdx * spanWitdh + alignbaseTitle.length();
		int preItemIdx = alignbaseTitleIdx - 1;
		int preItemEndPos = lastPos;
		return leftpadPrice(preItemEndPos, lastPos, item, map);
	}

	static String rightAlign(String item, String alignbaseTitle, int alignbaseTitleIdx, String preItemMapKeyname,
			Map map) {
		int lastPos = alignbaseTitleIdx * spanWitdh + alignbaseTitle.length();
		int preItemIdx = alignbaseTitleIdx - 1;
		int preItemEndPos = preItemIdx * spanWitdh + map.get(preItemMapKeyname).toString().length();
		return leftpadPrice(preItemEndPos, lastPos, item, map);

	}

	private static String baseTitle(String string) {
		// TODO Auto-generated method stub
		return string;
	}

	private static String rightAlign(Object item, String alighBaseItem, int alighBaseItemIdx, int lastItemEndPos) {
//		int ItemIdx=3;String lastItemTitle="price";

		// int lastItemEndPos="total:".length();
		// int nowGridIdx=3;

		int lastPos = 2 * spanWitdh + alighBaseItem.length();
		return leftpadPrice(lastItemEndPos, lastPos, item);
	}

	private static String leftpadPrice(int preItemEndPos, int lastPos, String item, Map map) {
		int padWItem = lastPos - preItemEndPos;
		return StringUtils.leftPad("$" + map.get(item).toString(), padWItem);
	}

	private static String leftpadPrice(int lastItemEndPos, int lastPos, Object item) {
		// int lastPos=+item.length();
		int padWItem = lastPos - lastItemEndPos;
		return StringUtils.leftPad("$" + item.toString(), padWItem);
	}

	private static String leftpad(int lastItemEndPos, String string) {
		// TODO Auto-generated method stub
		return null;
	}

//	private static String leftpad(int itemIdx, String lastItemTitle, String string) {
//		 
//		
//	}

	static String rightPad(Object s) {
		int col_len = 20;
		return StringUtils.rightPad(s.toString(), col_len);
	}

	static String rightPad40(Object s) {
		int col_len = 36;
		return s.toString() + StringUtils.rightPad("", col_len - s.toString().length());
	}

	static String leftPad_pricepad(Object price) {
		int col_len = 6;
		String prc = "$" + price.toString();
		return StringUtils.rightPad("", col_len - price.toString().length()) + prc.toString();
	}

	private static String leftPad_pricepad(Object price, int priceLastIndex, Object preObj) {
		int col_len = 6;
		String prc = "$" + price.toString();
		return StringUtils.leftPad(prc.toString(), priceLastIndex - preObj.toString().length());
	}

	// 计算单项税务
	private static Object get_item_tax(Integer qty, double price, Object tax_rate_num) {
		if (tax_rate_num == null)
			return 0d;
		return qty * price * (double) tax_rate_num;

	}

	// 查询税率 数据表选择运算
	private static Map selectTaxrateFrom_taxRateTable_where_loc_and_itemtype(Object loc, Object itemtype) {

		List<Map> result = taxRateTable.stream().filter(map_item -> {

			return loc.equals(map_item.get("loc地点").toString()) && itemtype.equals(map_item.get("type").toString());
			// return true;

		}).collect(Collectors.toList());
		return result.get(0);

	}

	// 查询物品类型
	private static Object gettype(Object name) {
		List<Map> itemTypetable = new ArrayList() {
			{
				// loc,sale,type(food)
				add(new HashMap() {
					{
						put("item物品名", "potato chips");
						put("type", "food");

					}
				});
				add(new HashMap() {
					{
						put("item物品名", "shirt");
						put("type", "cloth");

					}
				});
			}
		};
		List<Map> result = itemTypetable.stream().filter(map_item -> {

			return name.equals(map_item.get("item物品名").toString());
			// return true;

		}).collect(Collectors.toList());
		if (result.size() == 0)
			return "other";
		return result.get(0).get("type");
	}

	// 税率表
	private static List<Map> taxRateTable = new ArrayList() {
		{
			// loc,sale,type(food)
			add(new HashMap() {
				{
					put("loc地点", "CA");
					put("type", "other");
					put("tax rate税率", "9.75%");
					put("tax_rate_num税率数字格式", 0.0975);
				}
			});
			add(new HashMap() {
				{
					put("loc地点", "CA");
					put("type", "food");
					put("tax rate税率", "0");
				}
			});

			add(new HashMap() {
				{
					put("loc地点", "NY");
					put("type", "other");
					put("tax rate税率", " 8.875%");
					put("tax_rate_num税率数字格式", 0.08875);
				}
			});
			add(new HashMap() {
				{
					put("loc地点", "NY");
					put("type", "food");
					put("tax rate税率", "0");
				}
			});
			add(new HashMap() {
				{
					put("loc地点", "NY");
					put("type", "cloth");
					put("tax rate税率", "0");
				}
			});

		}
	};

}
