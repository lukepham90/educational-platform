package com.educational.platform.handler;

import com.educational.platform.common.domain.CommandHandler;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "com.educational.platform")
public class CommandHandlerTests {

    @ArchTest
    public static final ArchRule commandHandlers_shouldHave_nullabilityAnnotations = methods()
            .that().arePublic()
            .and().doNotHaveRawReturnType(new DescribedPredicate<>("native or void") {
                @Override
                public boolean apply(JavaClass javaClass) {
                    return javaClass.isPrimitive() || javaClass.isEquivalentTo(Void.TYPE);
                }
            })
            .and().areDeclaredInClassesThat().implement(CommandHandler.class)
            .should().beAnnotatedWith(NonNull.class).orShould().beAnnotatedWith(Nullable.class)
            .because("Command handlers should provide detailed documentation of API, it's why nullability annotations are needed.");

    @ArchTest
    public static final ArchRule commandHandlers_shouldHave_nameEndingWithCommandHandler = classes()
            .that().implement(CommandHandler.class)
            .should().haveSimpleNameEndingWith("CommandHandler")
            .because("Command handlers should have common naming convention.");

}
