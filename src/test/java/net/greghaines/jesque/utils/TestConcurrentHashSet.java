package net.greghaines.jesque.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestConcurrentHashSet {

    @Test
    public void testSingleElem() {
        final ConcurrentHashSet<String> set = new ConcurrentHashSet<>();
        final String foo = "foo";
        Assert.assertTrue(set.isEmpty());
        Assert.assertEquals(0, set.size());
        Assert.assertFalse(set.contains(foo));
        Assert.assertFalse(set.remove(foo));
        
        Assert.assertTrue(set.add(foo));
        Assert.assertFalse(set.add(foo));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(foo));
        
        final Object[] arr1 = set.toArray();
        Assert.assertNotNull(arr1);
        Assert.assertArrayEquals(arr1, new Object[]{foo});
        
        final String[] arr2 = new String[set.size()];
        final String[] arr3 = set.toArray(arr2);
        Assert.assertNotNull(arr3);
        Assert.assertSame(arr2, arr3);
        Assert.assertArrayEquals(arr3, new String[]{foo});
        
        Assert.assertTrue(set.remove(foo));
        Assert.assertFalse(set.remove(foo));
        Assert.assertTrue(set.isEmpty());
        Assert.assertEquals(0, set.size());
        Assert.assertFalse(set.contains(foo));
    }
    
    @Test
    public void testMultiElem() {
        final ConcurrentHashSet<String> set = new ConcurrentHashSet<>(4);
        final String foo = "foo";
        final String bar = "bar";
        final String baz = "baz";
        final String qux = "qux";
        final List<String> fullList = Arrays.asList(foo, bar, baz, qux);
        final List<String> noQux = Arrays.asList(foo, bar, baz);
        final List<String> noFooBar = Arrays.asList(baz, qux);
        
        Assert.assertTrue(set.addAll(fullList));
        Assert.assertFalse(set.addAll(noQux));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(fullList.size(), set.size());
        Assert.assertTrue(set.containsAll(fullList));
        Assert.assertTrue(set.contains(foo));
        Assert.assertTrue(set.contains(bar));
        Assert.assertTrue(set.contains(baz));
        Assert.assertTrue(set.contains(qux));

        Assert.assertTrue(set.retainAll(noQux));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(noQux.size(), set.size());
        Assert.assertTrue(set.containsAll(noQux));
        Assert.assertTrue(set.contains(foo));
        Assert.assertTrue(set.contains(bar));
        Assert.assertTrue(set.contains(baz));
        Assert.assertFalse(set.contains(qux));

        Assert.assertTrue(set.removeAll(noFooBar));
        Assert.assertFalse(set.isEmpty());
        Assert.assertEquals(2, set.size());
        Assert.assertTrue(set.contains(foo));
        Assert.assertTrue(set.contains(bar));
        Assert.assertFalse(set.contains(baz));
        Assert.assertFalse(set.contains(qux));
        
        set.clear();
        Assert.assertTrue(set.isEmpty());
        Assert.assertEquals(0, set.size());
    }
}
