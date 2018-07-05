package me.quexer.homeserver.serverapi;

import com.google.gson.Gson;
import me.quexer.homeserver.serverapi.commands.CoinsCMD;
import me.quexer.homeserver.serverapi.commands.GamemodeCMD;
import me.quexer.homeserver.serverapi.commands.NickCMD;
import me.quexer.homeserver.serverapi.commands.TeleportCMD;
import me.quexer.homeserver.serverapi.listeners.ChatListener;
import me.quexer.homeserver.serverapi.listeners.NickListener;
import me.quexer.homeserver.serverapi.listeners.TablistListener;
import me.quexer.homeserver.serverapi.quickyapi.api.InventoryHandler;
import me.quexer.homeserver.serverapi.quickyapi.api.LocationAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.quickyapi.database.AsyncMySQL;
import me.quexer.homeserver.serverapi.quickyapi.database.MongoManager;
import me.quexer.homeserver.serverapi.utils.NickAPI;
import me.quexer.homeserver.serverapi.utils.SoundManager;
import me.quexer.homeserver.serverapi.utils.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class ServerAPI extends JavaPlugin {

    private static ServerAPI instance;
    private static Gson gson;
    private static MongoManager mongoManager;
    private static AsyncMySQL asyncMySQL;
    private static LocationAPI locationAPI;
    private static InventoryHandler inventoryHandler;
    private static String prefix;
    private static String coinsPrefix;
    private static SoundManager soundManager;
    private static UserManager userManager;
    private static boolean nickOnThisServer;
    private static boolean setTablist;


    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void init() {
        new AsyncTask(() -> {
        setInstance(this);
        setGson(new Gson());
        setLocationAPI(new LocationAPI());
        setMongoManager(new MongoManager("localhost", 27017, "HomeServer"));
        setInventoryHandler(new InventoryHandler());
        setPrefix("§8» §6§lServer §8➜");
        setSoundManager(new SoundManager());
        setUserManager(new UserManager());
        setCoinsPrefix("§8» §e§lCoinsAPI §8➜");
        Bukkit.getPluginCommand("coins").setExecutor(new CoinsCMD());
        Bukkit.getPluginCommand("gamemode").setExecutor(new GamemodeCMD());
        Bukkit.getPluginCommand("teleport").setExecutor(new TeleportCMD());
        Bukkit.getPluginCommand("nick").setExecutor(new NickCMD());
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new NickListener(), this);
        Bukkit.getPluginManager().registerEvents(new TablistListener(), this);
        System.out.println("Alle Klassen initialisiert!");

            NickAPI.getNames().addAll(Arrays.asList(" 80083 "," 500008367 "," _solo "," 1037cky "," 123bball "," 17hunter00 "," 1999franklin "," 1gigabat "," 1johnclaude "," 1zak11 "," 22chad22 "," 270midnight "," 321MINECRAFt321 "," 5k337 "," 9001RageQuits "," 93simon "," 9falcon "," A5002 "," A51assassin "," a9ch4f "," aac93 "," aag147 "," Aaroncvx "," aaronon123 "," ABCCOOL "," abcgodyo "," abcman1000 "," Ac1Baddie "," ACarroll "," accountgmail "," acerkake "," ACS1997 "," Adamacious "," adamonfire2 "," Addracyn "," AdeptAlvin "," Adidapro "," adlzman "," Adridoole "," aerbex "," afguy20041 "," aftertast "," Aganhym "," AJD25 "," ajsl11 "," Akechi_Jside "," AlbinoElfBoy "," AlCapwner017 "," aleebs "," AliCM333 "," Alienate "," Alkoed "," alle28 "," Amira01 "," andersg2000 "," andre0211 "," AndyS2 "," antflga "," ArcAngelDarkness "," armbot "," arowsmitt "," Arrrg "," AshrafAnsari "," askesejr "," asona56 "," Asparagus "," Asquilader "," aussielauren "," auz1394 "," awesomefooty "," awesomegun12 "," awesomenick01 "," awesomeOmega17 "," Awesominator01 "," AxF_Rippen "," axsne "," ayenore123 "," azbkthompson "," azgoo "," azix805 "," bacardee "," Baelish "," baka245 "," bakemebread "," BAMitisME "," Bammargera23 "," Bansil "," Barbarian22 "," baseball6366 "," Basemind "," bast_e "," BaZoOKaMoE "," bbqhax "," bcasey99 "," Beavisback "," Beecher77 "," Ben_v3 "," bencmad "," Bender200 "," benstone "," bergqvist95 "," bestmobbin "," bick_boy2423 "," bidoumofimothep "," big3mac "," bigboy114 "," bigfootyeti "," bignelx3 "," bigwoop47 "," bikmama298 "," bing190 "," binredbin "," bkr33 "," blackcat777 "," blackwater403 "," Blade578 "," blade81 "," blakecooper21 "," blakejames "," blar210 "," blaze425 "," blazedmajor "," BLiNK "," blobfish12 "," Blokkiesam "," blouchiwa "," blown4six "," Bluecar15 "," bluudgeoned "," bmansmanblink182 "," bob13377 "," bob606060 "," bobppy1 "," bobyn123 "," boldbob "," bombmaker13 "," BoneHunter "," boothboy "," boriiboy "," born_of-osiris "," Borox "," boy_ruby_fly "," Bradman5000 "," bradrocks "," brain_knight "," brent8 "," brianc1000 "," Brodskin "," Bsully "," bubble5454 "," buddy88899 "," BuffyTVS "," Bunkham "," buster88 "," buy2k8 "," Bwill9886 "," bzoro1 "," Cadvan "," CALTxBEACH "," calvinatorz "," camazezy "," camcar1 "," Canedew "," Canedis "," Capt_Danger_ "," captainran "," captiansprinklez "," carden777 "," CARDTRICKSTER74 "," cardzgamez "," carletto66 "," CashRJ "," catcat2 "," cbauer1127 "," cchuck "," Cdavis "," ceejay1022 "," celsderg "," cgcrouse882 "," cGrows658 "," ch0pper116 "," chadd1 "," chadley253 "," chadwick12 "," chane17 "," chaos762 "," chaosbelow "," chaosknux "," chaosmac "," chapers "," charlie78 "," charliekleiman "," chase33 "," chase333 "," chaser132 "," chaserbob1 "," chavey6 "," chayeswr "," chayton50000 "," chaz200 "," chazer123 "," Cheasify "," cheatinghater "," cheedos "," cheekybigtom "," cheers "," cheese202 "," cheesypewfs "," cheetahseyes "," cheezbox "," chento47 "," cherle "," chicco713 "," chicken500 "," chiefyy "," chilli99 "," chip02 "," chipandrew "," chocky10 "," ChocolatePancake "," chody4life "," cholo1280 "," cholsclaw "," chopsey1998 "," chosin137 "," Chowsta890 "," chri02t9 "," chris123990 "," chris440 "," chrisbetha "," chrisc377 "," chrisjarman231 "," chrisjr54 "," chrisseh "," christianss11 "," christianvork "," Christoffer1881 "," Chucklecow "," chukonu "," chune0413 "," ciaranb64 "," cici820 "," cilence "," cirqueman "," cj1124 "," cjm519 "," cjoli "," clackson "," Clammers "," clarkkent12 "," classic594 "," claudia1 "," claudiopwns "," cleggy123 "," CleteKillz "," clone01 "," clover367 "," Clubwho "," clumzy2me "," cmoran2011 "," cmt0726 "," coalas "," CobaltArchangel "," coco113 "," cocosboy10 "," codfishking44 "," codk1ng2442 "," cody4209  "," codyalan "," codyj110 "," colbyh "," colbyo "," coleyb "," colinmccook "," coloneltrip "," colserra "," comer42008 "," comete99 "," ComicGuy89 "," commder12345 "," compaq435 "," compuguy "," conan87 "," concot "," condor6808 "," conflictt "," Conman979 "," conner5 "," connerking "," conor6808 "," conorhodge "," consoso "," cookie10 "," cookie1337 "," cookieman511 "," cool_dude11 "," cool03 "," cooldude179 "," coolmatt30 "," coolmonkeyguy "," coolranger "," coolwalker "," coothmagi "," Cootsy "," copley123 "," coreylawd "," corgblam "," cornerdweller "," costar96 "," costou12 "," couga13 "," cougar88 "," couragealways "," courageman "," cpeezy "," craftwolf "," CraftyLop "," crancmichi "," crandall123 "," crapmaster2000 "," crawler600 "," crayfrog "," crazydwarf "," crazyhorse931 "," creaturemagic "," creepabusster "," creeperded "," creezcreez1 "," Cresof "," crgracer "," criusbizzness "," crossedalarm "," crostos "," crsuttonii "," crusader1 "," crushhomer "," cruyff14 "," crystalkit "," crystall112 "," cs5n531 "," ctrain101 "," cuatro44 "," cubemam "," curioused "," Cybersubzero "," D3M0NiiCx "," DA_SWAMPMONSTA "," DAC30100 "," dagunner "," DaGutie44 "," dallas13 "," dallasbates "," damham123 "," damien74500 "," dancingbrave "," dandilion "," danelewis "," daniel1206 "," danielsen3 "," danizcool1 "," danrz_ "," danthemandanny "," darkopal2000 "," darkpoet "," darkwalker247 "," darthwader "," daypig "," deadlyiron "," Deadricq "," Deeler555 "," defender14 "," demon1972 "," DerkiDerp "," derwolfpak "," desimoner "," Destroyerkid "," detroit "," devilgrl24 "," DGM1998 "," dgraves "," dgsbgm35 "," didd0352 "," dietjake "," dillan90 "," dimmes "," dixib "," djbomber "," DJIzzyB13 "," djthugcats "," DodgemDynamite "," DomBoii35 "," domthebomb123 "," domvito123 "," Doo_monkey "," doobydoo "," dookie "," doomflame99 "," doomkittyx "," drabs "," dragonfan98 "," Drakken132001 "," drdiggler "," drfisk "," driftking4422 "," drkollins "," drpoonhammer "," dwall45 "," ebag1102 "," elliotmcr "," ELMacheteRapido "," emerica2 "," Endergeek123 "," ericcronin "," ericwold2 "," evelyn2 "," ewant12 "," faiilure "," Fallacy "," fanta918 "," farrar1 "," FaTaLwassap "," fcb2009 "," fibbsjc "," finfinfin90 "," flames12 "," flaming7 "," flunkey10 "," flyboy7 "," flyers418 "," fratesi "," fredddi "," fuzzy88 "," Gabora "," gamemusic "," gamerAJ313 "," garys "," Geek511 "," generalhowe "," giulio98 "," gloeglm "," gnomar97 "," gnome47 "," goblinking20 "," GodCZ "," gonzalez3 "," Grenden "," GUNNAR10 "," hamara "," hannahcao "," hansjoergl "," harry0214 "," hellshell "," henryisgood "," herobrine1o1 "," hiphoplary "," hjerrild123 "," Hoags11 "," hoborocks22 "," Holden2007 "," hopp1 "," Huggles "," iceman "," iluvscooters "," imji "," ImNotVino "," invader001 "," j8kereeve6 "," jackupz "," jacob4431 "," jacoba1001 "," jamisonj "," jamosaur5164 "," jan12345 "," janie8 "," JAWS86 "," jaxman "," jbean99 "," jbeaudette "," jerl999 "," jipjipjip "," jitske "," jmp100 "," joenmb "," johegg "," john7067 "," johnhope "," jonahtaco "," jonathan1321 "," joshmc10 "," jpaterson "," jtmf26 "," juzz38 "," kale01 "," kalfin "," karen12345 "," KatieWall "," kazuod "," keichan "," kevinbo "," kholdbladez "," kiastoon "," Kildar "," killero "," KillerO "," Kinorana "," klette "," kmoore197 "," knifeonly "," Knuckel "," koko17 "," Kooldexkiller99 "," kosmo272 "," kris4085 "," krullebol911 "," krycochan "," LaicaF "," lala25 "," Lamanch "," laotao "," lasercam "," Leadpipe "," Lederjacke "," leftone "," lemonlary "," Leslee "," letterzetter "," lily2525 "," livingecho "," lol217327 "," lolman6 "," lolzlord55 "," lord_ethan "," love4games "," LtRegicide "," luckys16 "," lucyy "," lukasmendez "," lukeboss "," lukerichardson "," macejoe "," manu75176 "," MarinePinguin "," mateoledoux "," mathias12310987 "," mattiflo2 "," mattikott "," Matty90 "," meerca8 "," megalennie1 "," Messy_Turkey "," mgk103 "," Michelle "," mikedaboom "," MikeLitterous "," mikeyhart "," minecraftmoes "," mjd222 "," mmerlin "," moshi01 "," mpf88 "," mrmango69 "," MrPicard "," murdlih "," nastaman "," natean "," nature_move "," neonxp "," nerd1998 "," nessling "," nextdoor "," nflboy717 "," nicholas2001 "," nichos "," nick121224 "," nickf62 "," nigel123 "," NightScope "," nikit01 "," niks "," ninja32 "," nonatz "," norberedv1 "," Nuttmegger "," odinlevi "," ogdenw "," oldhoj "," oldroland "," omega666 "," omegaprime "," OprahChrist "," oscar01001 "," panchito19999 "," Passes "," PeepingTom24 "," Pendar "," pepsi456 "," pepthedog05 "," percal "," perryplat98 "," phantom11 "," philbob "," philip_grant "," pickle40 "," pie0017 "," PieGuy "," pigman21 "," pinky_muffy "," pipola "," piyrwouteq "," pk1998 "," plokinub "," pmpatrickrock "," pnut03 "," pod455 "," poethead390 "," pokejacko "," pompmaker1 "," pongolongo "," porty101 "," powerful_wizard "," ppsstt "," psychoanimal "," quant345 "," Randdalf "," ratata "," Rawn "," Raybeez55 "," rebelcletus "," reccanize "," reddeadrex "," reecekal "," reeferuk "," Renegade_Legacy "," Robablob "," robbekes "," robdee "," robotronic "," rockpee "," rogerthemaestro "," Romulad "," roverttt "," roxas4117 "," rumblefish "," runthistown "," rutetid "," ry22an "," Ry22an "," ryamglavin "," ryan_mc_dermo "," saber777 "," saipan "," saku1000 "," sakul1000 "," sakux "," sam444 "," sambridger "," sammydog1997 "," samperkins9 "," samromer "," sanathas "," scorpio10 "," scout1501 "," Seedy_George "," sellbram "," semoyu "," shak1145 "," shaun1998 "," shgn "," ShineKnuckleJoe "," shmuck_coco "," shrader "," Singular "," Sivlon "," Sk1llsT3R "," skateelias "," slim08 "," smilimaxim "," snikerfreak "," snoopy81 "," Snoxhzuni "," soaringeagle963 "," spawnuk86 "," speeeder0615 "," spikey123 "," spin810 "," spuzza9999 "," ssstephane "," starklaw "," statiikfury "," statix138 "," stickzer0 "," Stimmy "," Stuk "," suadeo "," superfes "," supergoalie9 "," supersandvich "," Supershatzer "," sureynot "," svitch "," sweenyb "," talaknor "," tapout10 "," Tarheels96 "," taski165 "," taxman69 "," tbrynner "," tcg528 "," Tech "," texasmgolf "," thamightybobo "," thaxtonian "," The_Snowbro "," thebrats "," theFabrosi "," theharribokid "," thelovefamily "," themaskedman "," thenate217 "," theSero "," Thetangledhand "," thethebigman01 "," thethrill999 "," TheTwig "," Thijscream "," ThtsWatSehSaid "," tillyg98 "," timchen "," timonde "," timperi "," tiuri730 "," tjraff01 "," toddy96 "," toddy96 "," tommy623 "," tomper123 "," Topo15 "," tornmage "," tpayne18 "," tronboy "," Troy49 "," TrueFreak "," tsim49 "," ttreg123 "," tupitupa "," twomack33 "," TylenolMeep "," tytoowns281 "," ukcats "," ultimatebag "," ur_dead "," ursasmar "," Ursula "," vanceboot "," vchammer2 "," vempire "," viking58 "," vortex308 "," vvonders "," waltisawesome "," wanderlust99 "," weas90 "," whothat17 "," wiff98 "," wilde_katze "," wildii "," WilliamBoo "," wolpaladin "," Wondwi "," x_Kyle "," xApexPredator "," xDaKillerx "," xi0n "," xitone "," xxsavagesteviexx "," Z3RU "," zachary1231 "," zebbe9999 "));

        });
    }

    //<editor-fold desc="Util-methods">
         public void removeMetadata(Player p, String metadata) {
            p.removeMetadata(metadata, this);
         }
         public void setMetadata(Player p, String metadata, Object value) {
            removeMetadata(p, metadata);
                p.setMetadata(metadata, new FixedMetadataValue(this, value));
         }
    //</editor-fold>


    public static boolean isSetTablist() {
        return setTablist;
    }

    public static void setSetTablist(boolean setTablist) {
        ServerAPI.setTablist = setTablist;
    }

    public static boolean isNickOnThisServer() {
        return nickOnThisServer;
    }

    public static void setNickOnThisServer(boolean nickOnThisServer) {
        ServerAPI.nickOnThisServer = nickOnThisServer;
    }

    public static String getCoinsPrefix() {
        return coinsPrefix;
    }

    public static void setCoinsPrefix(String coinsPrefix) {
        ServerAPI.coinsPrefix = coinsPrefix;
    }

    public static SoundManager getSoundManager() {
        return soundManager;
    }

    public static void setSoundManager(SoundManager soundManager) {
        ServerAPI.soundManager = soundManager;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static void setUserManager(UserManager userManager) {
        ServerAPI.userManager = userManager;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        ServerAPI.prefix = prefix;
    }

    public static ServerAPI getInstance() {
        return instance;
    }

    public static void setInstance(ServerAPI instance) {
        ServerAPI.instance = instance;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        ServerAPI.gson = gson;
    }

    public static MongoManager getMongoManager() {
        return mongoManager;
    }

    public static void setMongoManager(MongoManager mongoManager) {
        ServerAPI.mongoManager = mongoManager;
    }

    public static AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }

    public static void setAsyncMySQL(AsyncMySQL asyncMySQL) {
        ServerAPI.asyncMySQL = asyncMySQL;
    }

    public static LocationAPI getLocationAPI() {
        return locationAPI;
    }

    public static void setLocationAPI(LocationAPI locationAPI) {
        ServerAPI.locationAPI = locationAPI;
    }

    public static InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public static void setInventoryHandler(InventoryHandler inventoryHandler) {
        ServerAPI.inventoryHandler = inventoryHandler;
    }

}
