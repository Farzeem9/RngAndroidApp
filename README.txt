#RnG Android App
***********************************************************************************************
*****************************Created By the Android Developers*********************************
***********************************************************************************************

Description:
Rng is an android application which allows hosting your own advertisements of products and services for
renting purposes. It allows users to connect personally without any 3rd party interfering. It does this
using a LAMP(Linux, Apache , MySQL, PHP) stack on the server for pushing and pulling data on to the app.
The Description of all files are as follows -


Contents- 
Java Files in alphabetical order

AccordionTransformer
ActiveAdsFragment
AdActivity
AdFragment
AdLinksActivity
Ads
AdWishlistFragment
Album
AlbumsAdapter
AsyncResponse
BaseAnimationInterface
BaseSliderView
BaseTransformer
Blur
BlurC
BlurTask
Category_List
CheckNotificationService
Config
DeactiveAdsFragment
DescriptionAnimation
EditAdActivity
EditServiceActivity
EncryptDecrypt
Filter
FilterActivity
FilterAdapter
FilterServiceActivity
FirstActivity
FixedSpeedScroller
GenericAsyncTask
GetJSON
HomeAdapter
HomeFragment
ImageFragmentPagerAdapter
InfinitePagerAdapter
InfiniteScrollviewService
InfiniteViewPager
InfScrollviewListener
LoginActivity
MainActivity
MultiSliderFragmentRange
MyActiveAdsAdapter
MyActiveServiceAdapter
MyAdActivity
MyAds
MyAdsAsyncTask
MyDeactiveAdsAdapter
MyDeactiveServiceAdapter
MyPendingAdsAdapter
MyPendingServiceAdapter
MyServiceActivity
NewAdActivity
Newaddupload
Notification
NotificationActivity
NotificationAdapter
NotificationReceiver
NotificationServiceStarterRepeater
onListenerScroll
PagerIndicator
PendingAdsFragment
PriceSliderFragment
ProfileFragment
RateActivity
SearchActivity
SearchAdsFragment
SearchServicesFragment
SearchViewActivity
SendNoti
Service_Category
ServiceActiveFragment
ServiceActivity
ServiceAlbum
ServiceAsyncTask
ServiceCategoryAdapter
ServiceDeactivatedFragment
ServiceFragment
ServicePendingFragment
ServiceTabFragment
ServiceWishlistFragment
SliderAdapter
SliderLayout
SortFragment
SwipeFragment
TabFragment
TextSliderView
TransformerAdapter
ViewPagerEx
WishlistAdapter
WishlistFragment





1.
##############################################################################
AccordionTransformer
##############################################################################

Description:
BaseTransformer helper class for the ImageSlider in AdActivity
Used in sliderlayout to define position of the image slider

Methods
protected void onTransform(View view, float position):
used to override onTransform method of the BaseTransformer class
defines positional features of the imageslider
#Do not modify

2.
##############################################################################
ActiveAdsFragment
##############################################################################

Description:
Fragment for the Active Ads page. Defines the page layout and loads data from server using 
MyAdsAsyncTask class. Loads only the Active ads of the current user

Methods:
onCreateView:
Overrides the method from parent class and creates MyAdsAsyncTask to load Ad from server

3.
##############################################################################
AdActivity
##############################################################################

Description:
Activity which loads the product advertisement. 


Variables used in the class:

    private SliderLayout mDemoSlider;                                                               -ImageSlider
    private TextView name,desc,rent,date,city,age,deposit,crent,maxrent,tvrentw,tvrentm;            -All textviews
    private String aid;                                                                             -AID of the ad
    private MenuItem star;                                                                          -MenuItem for wishlist
    private Button rating_comments;                                                                 -Button for rating_comments
    private RatingBar ratingBar;                                                                    -Rating Bar
    private String canrent;                                                                         -String which will be 0 or 1 based on whether the current user can rate the Ad or not
    private boolean set=false;                                                                      -Boolean to check if wishlist is set or not
    private Toolbar toolbar;                                                                        -Toolbar
    private RadioGroup radioGroup;                                                                  -RadioGourp for the radiobuttons in Ad
    private RadioButton less,more,equal;                                                            -Radio Buttons for less,more and equal
    private boolean selected=false;                                                                 -boolean to check if a radiobutton was selected or not
    private String rentperiod;                                                                      -String which holds the desired rent period from selection of radio button
    private GetAd getAd;                                                                            -Instance of get Ad class to load the ad from server
    private GenericAsyncTask genericAsyncTask;                                                      -Instance of GenericAsyncTask class to load Users rating from the server

Methods:
1.linkParser():
Method used to parse link received from external broadcast (from sharing). Parses link to check if link is valid
Throws CustomException if link is not valid

2.error():
Method called whenever link received from intent is improper. Creates a new AlertBox to inform user that the link is invalid

3.onCreate():
Method overriden from parent class. Used to initialise all variables and start other methods.
starts GetAd to load all Ad data from the server
starts genericAsyncTask to load rating from server

4.onStop()
Method called whenever Activity is closed 
USed to stop ImageSlider,getAd and genericAsyncTask to prevent memory leak

5.onRent()
Method called to request the product. String message is passed which stores whether user wants to rent urgently or not.
Called by the onClickListeners of the rent now button.

6.onActivityResult()
Method overriden from parent class to receive intent after calling startActivityResult
Called if user is not logged in yet from Facebook.

7.static Month()
static function to get Month  from a given date d.

8.fillAdd()
function to fill the ad details received from server in the textviews of the Activity. Called by GetAd class.

9. onShare()
function called when share button is clicked

10.onRateandComment()
method to start the RateActivity class. Called when rate button is clicked

11. getMenus()
method to load the value of the wishlist icon in the Activity. called when MenuItems are created.

12.onCreateaOptionsMenu()
method called by android when MenuItems are created.

13.onOptionsItemSelected()
Method called by android when MenuItems are clicked. Used to toggle state of wishlist button and share button.

14.onUrgentRent()
Method called when rent button is touched. Calls onRent() method.

4.
##############################################################################
AdFragment
##############################################################################

Description:
Fragment which is used by user to upload new advertisement for a product. 

Methods-
1.refreshspinner()
Method used to refresh spinner data set once it has been selected at least once. called by the onItemSelectedListener of the Spinners.

2.showall()
Method to display all radio buttons. Called when the rent period selected is months

3.show_week()
Method to display day and week radio buttons called when rent period selected is weeks

4.show_day()
Method to display only day radio buttons. called when rent period selected is days.

5.hidePager()
Method used to hide the ImagePager. Called when backbutton is clicked when pager is open.

6.reInstantiatePager()
Method used to reInstantiate the pager whenever a new image is added/removed

7.submitForm()
Method which validates all input when user clicks submit button

8.onActivityResult
Method overriden from parent class. Called when user loads images from gallery or camera. Adds the image into the ArrayList images to send to the server

9.refreshRent()
Change view rent textviews on the basis of the selected rent period.

5.
##############################################################################
AdLinksActivity
##############################################################################

Description:
Activity used to add links of website in ServiceFragment. Checks the entered data for a valid URL and sends it to ServiceFragment


6.
##############################################################################
Ads
##############################################################################

Description:
Holder class for RecyclerViewAdapters used in the project
holds various information about the Ad.


7.
##############################################################################
AdWishlistFragment
##############################################################################

Description:
Fragment for the user wishlist for products. Loads all products wishlisted by user.

8.
##############################################################################
Album
##############################################################################

Description:
Holder class for Adapter used in Category_List activity. Holds various information about a product such as image link, price etcetera.

9.
##############################################################################
AlbumsAdapter
##############################################################################

Description:
Adapter class for the recyclerview in category_list activity. Uses Album to store information of products and displays them.
Contains all logic for recycler view items in category_list

10.
##############################################################################
AsyncResponse
##############################################################################

Description:
Interface created for GenericAsyncTask class. Has one method processFinish()

11.
##############################################################################
BaseAnimationInterface
##############################################################################

Description:
Interface used by ImageSlider to define image sliding animation.


#Do not change


12.
##############################################################################
BaseSliderView
##############################################################################

Description:
Abstract class used by ImageSlider to make your own sliderview.
#Do not change

13.
##############################################################################
BaseTransformer
##############################################################################

Description:
Abstract class which is used to define transformation sequence animation for the image slider

#Do not change

14.
##############################################################################
Blur
##############################################################################

Description:
Class used to blur the view as seen in HomeFragment

#Do not change

15.
##############################################################################
BlurC
##############################################################################

Description:
Helper Class for Blur class
Contains logic to form an image with blurred view.

#Do not change

16.
##############################################################################
BlurTask
##############################################################################

Description:
Helper class to Blur a view Asyncally.

#Do not change

17.
##############################################################################
Category_List
##############################################################################

Description:
Activity which loads the product advertisements of a category received from the homeFragment
Contains a recyclerView which lists all the products.
Also contains sort and filter functions

18.
##############################################################################
CheckNotificationService
##############################################################################

Description:
Class which is called by Android periodically to check for notification
If notification is available, sends a notification to user.

Methods:
nonoti()
Method called whenever there was no notification from the server

createNoti()
Method called whenever there was a notification from the server

18.
##############################################################################
Config
##############################################################################

Description:
File which contains static method/variables to allow global access for the project
Contains variables such as link to server to be used in every class


19.
##############################################################################
DeactiveAdsFragment
##############################################################################

Description:
Fragment to load all the deactivated ads from the user. Used to display all the Ads deactivated by the user


20.
##############################################################################
DescriptionAnimation
##############################################################################

Description:
Example class to show how to create custom animation. Used in imageslider to animate sliding images

#Do not change 

21.
##############################################################################
EditAdActivity
##############################################################################

Description:
Activity to allow users to edit their Ad. 
Contains all logic similar to AdFragment except for an Activity.


22.
##############################################################################
EditServiceActivity
##############################################################################

Description:
Activity to allow users to edit their service Ad.
Contains all logic similar to ServiceFragment except for an activity.


23.
##############################################################################
EncryptDecrypt
##############################################################################

Description:
Helper class used to encrypt and decrypt data in AES 128 encryption. Used to encrypt userId in LoginActivity


24.
##############################################################################
Filter
##############################################################################

Description:
Holder class which holds information about the filters selected by users. Used by the FilterAdapter in FilterActivity.

25.
##############################################################################
FilterActivity
##############################################################################

Description:
Acitivity used by user to add filters to category_list. Contains logic to add filters to the sql code.

















