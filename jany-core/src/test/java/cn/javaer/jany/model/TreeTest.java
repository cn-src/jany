package cn.javaer.jany.model;

import cn.hutool.core.collection.CollUtil;
import cn.javaer.jany.model.pojo.Areas;
import cn.javaer.jany.test.JsonAssert;
import cn.javaer.jany.test.Log;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author cn-src
 */
class TreeTest {
    private final static List<Areas> TEST_DATA;

    private final static List<Areas> TEST_HAS_EMPTY_DATA;

    static {
        Areas areas1 = new Areas("河北省", "石家庄市", "长安区", 4L);
        Areas areas2 = new Areas("河北省", "石家庄市", "新华区", 1L);
        Areas areas3 = new Areas("河北省", "唐山市", "开平区", 2L);
        Areas areas4 = new Areas("山东省", "太原市", "小店区", 3L);
        TEST_DATA = Arrays.asList(areas1, areas2, areas3, areas4);

        Areas b1 = new Areas("河北省", "", "长安区");
        Areas b2 = new Areas("河北省", null, "新华区");
        Areas b3 = new Areas("河北省", "唐山市", null);
        Areas b4 = new Areas(null, "太原市", null);
        Areas b5 = new Areas("山东省", "", null);
        TEST_HAS_EMPTY_DATA = Arrays.asList(b1, b2, b3, b4, b5);
    }

    @Test
    void ofOneAllPropsEmpty() {
        final TreeConf<Areas> conf = TreeConf.of(areas ->
            new String[]{areas.getArea1(), areas.getArea2(), areas.getArea3()});
        final List<TreeNode> treeNodes = Tree.of(Collections.singletonList(new Areas()), conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofOneAllPropsEmpty.json",
            Log.json(treeNodes));
    }

    @Test
    void ofOneAllPropsEmpty_namedLeaf() {
        final TreeConf<Areas> conf = TreeConf.ofIgnoreLeafIfEmpty(Areas::getArea1, Areas::getArea2,
            Areas::getArea3);
        final List<TreeNode> treeNodes = Tree.of(Collections.singletonList(new Areas()), conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofOneAllPropsEmpty_namedLeaf.json",
            Log.json(treeNodes));
    }

    @Test
    void of() {
        final TreeConf<Areas> conf = TreeConf.of(areas ->
            new String[]{areas.getArea1(), areas.getArea2(), areas.getArea3()});
        final List<TreeNode> treeNodes = Tree.of(TEST_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.of.json", Log.json(treeNodes));
    }

    @Test
    void ofWithSort() {
        final TreeConf<Areas> conf = TreeConf.<Areas>builder()
            .namesFn(Areas::getArea1, Areas::getArea2, Areas::getArea3)
            .sortFn(Areas::getSort)
            .handler((info) -> {
                if (info.isLeaf()) {
                    info.put("sort", info.getModel().getSort());
                }
            })
            .build();
        final List<TreeNode> treeNodes = Tree.of(TEST_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofWithSort.json", Log.json(treeNodes));
    }

    @Test
    void ofWithDynamic() {
        final TreeConf<Areas> conf = TreeConf.<Areas>builder()
            .namesFn(Areas::getArea1, Areas::getArea2, Areas::getArea3)
            .handler(info -> {
                info.put("depth", info.getDepth());
                info.put("index", info.getIndex());
                info.put("leaf", info.isLeaf());
            })
            .build();
        final List<TreeNode> treeNodes = Tree.of(TEST_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofWithDynamic.json", Log.json(treeNodes));
    }

    @Test
    void ofWithDynamic_NAMED_LEAF() {
        final TreeConf<Areas> conf = TreeConf.<Areas>builder()
            .namesFn(Areas::getArea1, Areas::getArea2, Areas::getArea3)
            .handler(info -> {
                info.put("depth", info.getDepth());
                info.put("index", info.getIndex());
                info.put("leaf", info.isLeaf());
            })
            .emptyMode(TreeConf.EmptyMode.IGNORE_LEAF)
            .build();
        final List<TreeNode> treeNodes = Tree.of(TEST_HAS_EMPTY_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofWithDynamic_NAMED_LEAF.json",
            Log.json(treeNodes));
    }

    @Test
    void ofWithDynamic_BREAK_EMPTY() {
        final TreeConf<Areas> conf = TreeConf.<Areas>builder()
            .namesFn(Areas::getArea1, Areas::getArea2, Areas::getArea3)
            .handler(info -> {
                info.put("depth", info.getDepth());
                info.put("index", info.getIndex());
                info.put("leaf", info.isLeaf());
            })
            .emptyMode(TreeConf.EmptyMode.IGNORE_CHILDREN)
            .build();
        final List<TreeNode> treeNodes = Tree.of(TEST_HAS_EMPTY_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofWithDynamic_BREAK_EMPTY.json",
            Log.json(treeNodes));
    }

    @Test
    void ofHasEmpty() {
        final TreeConf<Areas> conf = TreeConf.of(Areas::getArea1, Areas::getArea2, Areas::getArea3);
        final List<TreeNode> treeNodes = Tree.of(TEST_HAS_EMPTY_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofHasEmpty.json", Log.json(treeNodes));
    }

    @Test
    void ofBreakEmpty() {
        final TreeConf<Areas> conf = TreeConf.ofIgnoreChildrenIfEmpty(Areas::getArea1,
            Areas::getArea2,
            Areas::getArea3);
        final List<TreeNode> treeNodes = Tree.of(TEST_HAS_EMPTY_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofBreakEmpty.json", Log.json(treeNodes));
    }

    @Test
    void ofNamedLeaf() {
        final TreeConf<Areas> conf = TreeConf.ofIgnoreLeafIfEmpty(Areas::getArea1, Areas::getArea2,
            Areas::getArea3);
        final List<TreeNode> treeNodes = Tree.of(TEST_HAS_EMPTY_DATA, conf);
        JsonAssert.assertEqualsAndOrder("model/TreeTest.ofNamedLeaf.json", Log.json(treeNodes));
    }

    @Test
    void toModel() {
        final TreeNode tn11 = TreeNode.of("石家庄市");
        final TreeNode tn1 = TreeNode.of("河北省", tn11);
        final TreeNode tn2 = TreeNode.of("山东省");
        final List<Areas> areas = Tree.toModel(Arrays.asList(tn1, tn2), strings -> {
            Areas areas1 = new Areas();
            areas1.setArea1(CollUtil.get(strings, 0));
            areas1.setArea2(CollUtil.get(strings, 1));
            areas1.setArea3(CollUtil.get(strings, 2));
            return areas1;
        });
        assertThat(areas).hasSize(2)
            .extracting(Areas::getArea1, Areas::getArea2, Areas::getArea3)
            .contains(
                tuple("河北省", "石家庄市", null),
                tuple("山东省", null, null)
            );
    }
}