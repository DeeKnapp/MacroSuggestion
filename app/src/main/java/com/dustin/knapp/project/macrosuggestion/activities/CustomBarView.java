package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dknapp on 7/13/17.
 */

public class CustomBarView extends View {

    Paint mBarPaint;

    public CustomBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBarPaint = new Paint();
    }


    //todo keep this light when creating custom views
    //todo on draw is called ever frame when rendering
    //todo example for lunch and learn with custom views >?>? ideas?
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}


/*
have to use google smart lock for instant apps...
instant app APKS hierarchy
        Base feature APK
    \/                   \/
Feature 1 APK      Feature 2 APK
feature 1 apk --LAUNCHES--> Feature 2 APK

1 instant app can have two features

App Module || Shared Modules || Instant App Feature Module
**Shared Library**
* base activities / base retrofit / base data storage
**Instant app feature module**
* code specific too feature (adapters / ui / activities)
*
 */


/*
Video Editing APIs
Media Codec
Media Extractor
**Ffmpef**
* build as executable and access via command line
download ndk and the ffmpeg code
libvpx - codec
place source code under ndk sources
(adding external libraries as executables executing with the ndk)
* build as shared library and have java interface with executable
first approach was slow and required app the run new task every time an edit / share was done
second approach is in progress. seems to be better approach />?
ffmpeg, ~10 seconds when compiled as shared library
 */

/*
**** Developer Options ****
* Allow mock locations (set mock locations -- brings you to app list selection...)
* Wait for debugger ( allows debugger to get set before onCreate is called allowing us to breakpoint earlier? )
* Show taps option ( see where the user clicking )
* Pointer location - x / y coordinates of touch, ( change in direction Xv Yv )
* Show layout bounds ( bounds of the view groups / views (margin optical bounds, clip bounds ))
* New in android O, default focused state colors - show layout bounds will show which views have focus at a given time
* Force RTL layout direction (force right to left layout)
* GPU overdraw ( view overdrawing each other --- using excess resources for the same views i.e. backgrounds on stacked views )
* Window animations slow down the draw speed for rendering the views / view groups
* Transition animations i.e. activities transitioning in app
* Show surface updates ( blinks pink as the screen is updating )
* Profile GPu Rendering ( show how long it takes to render certain screens in app - check if you are meeting 60 frames per second )
* Simulate color space ( helping accessibility testing ADA compliance - transform UI for different cases of color blindness )
* Don't keep activities ( will keep cache cleared, good for testing )
* Background process limits ( standard 20 processes, limit the number of process that are running simultaneously )
* System UI Demo Mode ( good for show and tells, etc ... )
 */


/*
Using SVGs & AVDs
Scalable Vector Graphics - Animated Vector Drawables
Lottie library
Shapeshifter (tool for avds)
Bezier Curves
Number tweening
 */

/*
Kotlin basics
lambda expressions
visibility modifiers
 */

/*
Spready your wings with Spannables
https://docs.google.com/presentation/d/1quGt2o-_aOn9ubqZ_mnJqiSsQ4vIOb_0LAz-mGD3Duc/edit?usp=sharing
 */

/*
Performance Optimizations
hierarchy viewer
gfxinfo ( look into number of views in package )
meminfo <pkg>
Android studio profiling tools ( battery / cpu  memory )

 */