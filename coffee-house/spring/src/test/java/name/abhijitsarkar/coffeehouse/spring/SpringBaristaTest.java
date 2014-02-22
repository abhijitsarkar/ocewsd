/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

package name.abhijitsarkar.coffeehouse.spring;

import name.abhijitsarkar.coffeehouse.Barista;
import name.abhijitsarkar.coffeehouse.Coffee;
import name.abhijitsarkar.coffeehouse.support.NotOnTheMenuException;
import name.abhijitsarkar.coffeehouse.spring.support.AppConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * @author Abhijit Sarkar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SpringBaristaTest {

    /* JSR-330 standard annotations.
     * http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/beans.html#beans-standard-annotations
     */
    @Inject
    private Barista barista;

    @Before
    public void setUp() {
        Assert.assertNotNull(barista);
    }

    @Test
    public void testMenu() {
        Assert.assertNotNull(barista.menu());

        Assert.assertNotNull(barista.menu().getBlends());
        Assert.assertNotNull(barista.menu().getFlavors());
    }

    @Test
    public void testServeDark() {
        Coffee dark = barista.serve("dark");

        Assert.assertEquals("Not the ordered blend.", Coffee.Blend.DARK, dark.getBlend());
        Assert.assertEquals("Not the ordered flavor.", Coffee.Flavor.NONE, dark.getFlavor());
    }

    @Test
    public void testServeDarkWithVanilla() {
        Coffee darkWithVanilla = barista.serve("dark", "vanilla");

        Assert.assertEquals("Not the ordered blend.", Coffee.Blend.DARK, darkWithVanilla.getBlend());
        Assert.assertEquals("Not the ordered flavor.", Coffee.Flavor.VANILLA, darkWithVanilla.getFlavor());
    }

    @Test(expected = NotOnTheMenuException.class)
    public void testWeekdayMenu() {
        barista.serve("decaf");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSuchBlend() {
        barista.serve("light");
    }
}
