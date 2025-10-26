package pages;

import annotations.UrlTamplate;
import annotations.Urls;

@Urls(urlTemplate = {
        @UrlTamplate(
                value= "/article/{1}",
                name = "template1"
        ),
        @UrlTamplate(
                value= "/article/{2}",
                name = "template2"
        )
}   )
@UrlTamplate(value = "article")
public class ArticlePage {
}
