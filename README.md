# Tog

Tog is a smart-log library that provides flexible work with logs using some of Gradle features.

## Why
Sometimes you need to have logs only in debug version and not in the release. In this case you should create method like:
```
public void log (String tag, String message) {
    if (BuildConfig.DEBUG) {
        Log.e("Hello", "world");
    }
}
```
and use it everywhere instead of calling just `Log#e` method for example.

But what if you want to show logs only for some flavor in debug version?
In this case you can create some method like:
```
public boolean isLogsEnabled() {
    return BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("flavorName");
}
```

But what if you have lots of conditions/rules?
Or maybe you want to show Toasts instead of Logs in some version?

Because of this I created this library.

## Basics
This library has three main instances:
- Tog – main manager class, from which you call log method.
- TogRule - rule that defines some use case (for example rule for some action when this is debug version)
- TogStyle - instance, that defines action (via TogListener), or shows that there should be no action.

## How to implement
There are few steps to implement:
- Create your own TogRule or use DefaultTogRule. In this TogRule you should define fields, that you'd like to check.
- Register current state of these fields: `Tog.init(new DefaultTogRule(BuildConfig.DEBUG));`
- Register some TogStyles for every use case and define action in listener if needed: `Tog.addTogStyle(new DefaultTogRule(false), new TogStyle());`
- You are ready to log something: `Tog.log(Log.WARN, MainActivity.class.getSimpleName(), "Hello world");`

## Use cases
### Show logs only for debug version
```
Tog.init(new DefaultTogRule(BuildConfig.DEBUG));
Tog.addTogStyle(new DefaultTogRule(true), new TogStyle(new TogListener() {
    @Override
    public void onTog(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message + "\n" + Log.getStackTraceString(throwable));
    }
}));
Tog.addTogStyle(new DefaultTogRule(false), new TogStyle());
Tog.log(Log.WARN, MainActivity.class.getSimpleName(), "Hello world");
```

### Show logs for some build types
At first, define custom buildConfigField in app build.gradle for release and debug, for example:
```
buildTypes {
    debug {
        buildConfigField 'boolean', 'LOGS_ENABLED', 'true'
    }
    release {
        buildConfigField 'boolean', 'LOGS_ENABLED', 'false'
    }
}

```
Then in code:
```
Tog.init(new CustomTogRule(BuildConfig.LOGS_ENABLED));
Tog.addTogStyle(new CustomTogRule(true), new TogStyle(new TogListener() {
    @Override
    public void onTog(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message + "\n" + Log.getStackTraceString(throwable));
    }
}));
Tog.addTogStyle(new CustomTogRule(false), new TogStyle());
Tog.log(Log.WARN, MainActivity.class.getSimpleName(), "Hello world");
```
You can see implementation of CustomTogRule in a sample app.

### Show logs for some build types with dynamic initialization
You can make previous case more dynamical. In app build.gradle:
```
defaultConfig {
    buildConfigField 'boolean', 'LOGS_ENABLED', project.hasProperty('logsEnabled') ? project.property('logsEnabled').toString() : 'false'
}
```
Then assemble project from command prompt using:
```
gradlew assemble -PlogsEnabled=true
```
or
```
gradlew assemble -PlogsEnabled=false
```

### Show logs for some flavor
For example you have some flavors in build.gradle:
```
productFlavors {
    flavor1 {
        applicationId "com.krossovochkin.tog.app"
    }
    flavor2 {
        applicationId "com.krossovochkin.tog.app"
    }
}
```
Then in code:
```
Tog.init(new CustomFlavorTogRule(BuildConfig.FLAVOR));
Tog.addTogStyle(new CustomFlavorTogRule("flavor1"), new TogStyle(new TogListener() {
    @Override
    public void onTog(int priority, String tag, String message, Throwable throwable) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}));
```
You can see implementation of CustomTogFlavorRule in a sample app.

Also, as you see, you can do everything you want in TogListener.
So, I think it might be useful.

If you have ideas how to do some else interesting things, you can tell me, or create pull request.
Also if you find some interesting use of this library, you can tell me too.

© Vasya Drobushkov, 2015
