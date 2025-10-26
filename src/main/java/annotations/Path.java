package annotations;

import com.google.inject.multibindings.StringMapKey;
import org.checkerframework.common.util.report.qual.ReportReadWrite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Path {
    String value();
}
