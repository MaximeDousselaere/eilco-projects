package spaceinvaders;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author DOUSSELAERE & EL RHOUFI
 */
public class SpaceInvaders extends Application{
    
    private Canvas canvas;
    private Pane root;
    private Scene scene;
    private GraphicsContext gc;
    private Fusee joueur;
    
    private ArrayList<Tir> tirs;
    private ArrayList<Tir> tirsAlien;
    private ArrayList<Alien> aliens;
    private ArrayList<Alien> aliensL2;
    private ArrayList<Alien> aliensL3;
    private int tailleAlien = 35;
    private int width = 500;
    private int height = 390;
    private int directionAlien; //0 = gauche 1= droite
    private int hauteurAliens=0;
    private int hauteurAliensL2=0;
    private int hauteurAliensL3=0;
    private int score;
    private boolean entreeEnSceneLigne2;
    private boolean entreeEnSceneLigne3;
    private boolean perdu;
    private boolean gagne;
    private boolean modeInsane;
    private int vie;
    private Timeline timeline;
    private AudioClip sonTirJoueur;
    private AudioClip sonAlienMeurt;
    private AudioClip sonJoueurMeurt;
    private AudioClip sonTirAlien;
    private AudioClip sonActive;
    private AudioClip sonDesActive;
    private AudioClip clicBouton;
    private AudioClip sonPerdu;
    private AudioClip sonGagne;
    private Media musiqueFond;
    private MediaPlayer mediaplayer;
    double volumeSons=0.5;
    double volumeMusique=0.5;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(width, height);
        clicBouton = new AudioClip("file:sons/clicBouton.wav");
        
        root = new Pane(canvas);
        root.setPrefSize(width, height);
        root.setStyle("-fx-background-image: url('file:images/backgroundMenu.png');");
        scene = new Scene(root);
        gc = canvas.getGraphicsContext2D();
        
        gc.setFont(new Font("TimesRoman", 45));
        gc.strokeText("Space Invaders", 10, 50);
        
        
        gc.setFont(new Font("TimesRoman", 15));
        gc.strokeText("Un virus extraterrestre s'approche de la terre, ", 150,330 );
        gc.setFont(new Font("TimesRoman", 15));
        gc.strokeText(" votre vaisseau est la dernière barrière !", 150,350 );
      

    	Button btnJeu = new Button();
    	
    	
    	btnJeu.setLayoutX(10);
    	btnJeu.setLayoutY(170);
    	btnJeu.setText("Jouer mode normal");
    	btnJeu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
              modeInsane=false;
                clicBouton.play();
                creerContenu(primaryStage);
            }    });
        root.getChildren().add(btnJeu);
    
	Button btnInsane = new Button();
	
	
	btnInsane.setLayoutX(10);
	btnInsane.setLayoutY(210);
	btnInsane.setText("Jouer mode difficile");
	btnInsane.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
          modeInsane = true;
            clicBouton.play();
            creerContenu(primaryStage);
        }    });
        root.getChildren().add(btnInsane);


        Button btnSettings = new Button();


        btnSettings.setLayoutX(10);
        btnSettings.setLayoutY(250);
        btnSettings.setText("Contrôles et sons");
        btnSettings.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
            clicBouton.play();
             menuSettings(primaryStage);
        }    });
        root.getChildren().add(btnSettings);


        Button btnInfo = new Button();


        btnInfo.setLayoutX(10);
        btnInfo.setLayoutY(290);
        btnInfo.setText("Information");
        btnInfo.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
            clicBouton.play();
        menuInformation(primaryStage);
            }    });
        root.getChildren().add(btnInfo);



        Button btnQuit = new Button();


        btnQuit.setLayoutX(10);
        btnQuit.setLayoutY(330);
        btnQuit.setText("Quitter");
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
      clicBouton.play();
    	primaryStage.close();
    }    });
        root.getChildren().add(btnQuit);


        //GESTION MUSIQUE ET BRUITAGE
        sonTirJoueur = new AudioClip("file:sons/joueurTir.wav");
        sonAlienMeurt = new AudioClip("file:sons/alienMeurt.wav");
        sonJoueurMeurt = new AudioClip("file:sons/joueurMeurt.wav");
        sonTirAlien = new AudioClip("file:sons/alienTir.wav");
        sonActive = new AudioClip("file:sons/sonActive.wav");
        sonDesActive = new AudioClip("file:sons/sonDesActive.wav");
        sonPerdu = new AudioClip("file:sons/sonPerdu.wav");
        sonGagne = new AudioClip("file:sons/sonGagne.wav");
        musiqueFond = new Media(new File("sons/musiqueFond.wav").toURI().toString());  
        mediaplayer = new MediaPlayer(musiqueFond);
        mediaplayer.setAutoPlay(true);  //on lance la musique au démarrage de l'appli
        mediaplayer.setVolume(volumeMusique);
        sonTirJoueur.setVolume(volumeSons);
        sonAlienMeurt.setVolume(volumeSons);
        sonJoueurMeurt.setVolume(volumeSons);
        sonTirAlien.setVolume(volumeSons);
        sonPerdu.setVolume(0.7);
        sonGagne.setVolume(0.7);
        sonActive.setVolume(0.1);
        sonDesActive.setVolume(0.1);
        clicBouton.setVolume(volumeSons-0.3);
        
    	primaryStage.setTitle("Space Invaders");
        primaryStage.getIcons().add(new Image("file:images/alien.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    	
     
    }
    	
    public void creerContenu(Stage primaryStage) {
        
    	canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        
        root = new Pane(canvas);
        root.setPrefSize(width, height);
        
        scene = new Scene(root);
        
        gc.setFill(Color.ROYALBLUE); //couleur de fond
        gc.fillRect(0, 0, width, height);

        int tailleJoueur = 35;        
        joueur = new Fusee(width/2-(tailleJoueur/2), height - tailleJoueur - 10, tailleJoueur, new Image("file:images/joueur.png"));
        
        joueur.affiche(gc); //affichage du joueur
                     
        aliens = new ArrayList<Alien>(); //Création de la liste d'aliens
        aliensL2 = new ArrayList<Alien>();
        aliensL3 = new ArrayList<Alien>();       
        tirs = new ArrayList<Tir>(); // création de la liste des tirs
        tirsAlien = new ArrayList<Tir>(); // création de la liste des tirs aliens
        directionAlien = 1;
        score=0;
        perdu = false;
        gagne = false;
        gc.setFont(new Font("TimesRoman", 15));         
        
        if(modeInsane){
            vie=1;
        }
        else{
            vie=3;
        }
        
        //Ajout de 5 aliens
        Alien a = new Alien(10,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a2 = new Alien(55,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a3 = new Alien(100,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a4 = new Alien(145,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a5 = new Alien(190,  20, tailleAlien, new Image("file:images/alien.png"));
        aliens.add(a);
        aliens.add(a2);
        aliens.add(a3);
        aliens.add(a4);
        aliens.add(a5);
        
        Alien a6 = new Alien(265,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a7 = new Alien(310,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a8 = new Alien(355,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a9 = new Alien(400,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a10 = new Alien(445,  20, tailleAlien, new Image("file:images/alien.png"));
        aliensL2.add(a6);
        aliensL2.add(a7);
        aliensL2.add(a8);
        aliensL2.add(a9);
        aliensL2.add(a10); 
        
        Alien a11 = new Alien(10,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a12 = new Alien(55,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a13 = new Alien(100,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a14 = new Alien(145,  20, tailleAlien, new Image("file:images/alien.png"));
        Alien a15 = new Alien(190,  20, tailleAlien, new Image("file:images/alien.png"));
        aliensL3.add(a11);
        aliensL3.add(a12);
        aliensL3.add(a13);
        aliensL3.add(a14);
        aliensL3.add(a15);
        
        // Gestion touches
        scene.setOnKeyPressed(e -> { 
            switch (e.getCode()) {
                case LEFT : //aller à gauche
                    joueur.moveLeftFusee();//on change sa position X
                    break;
                case RIGHT : //aller à droite
                    joueur.moveRightFusee();
                    break;
                case SPACE : //tirer 
                    tirs.add(joueur.tirer()); //on ajoute le tir du joueur à la liste des tirs
                    sonTirJoueur.play();
                    break;
            }
        
        });
        
        //création de la boucle de jeu : 
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e-> run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        //Création d'un bouton pour aller sur le menu : 
        Button btnMenu = new Button();
    	btnMenu.setLayoutX(0);
    	btnMenu.setLayoutY(0);
    	btnMenu.setText("Menu");
        
    	btnMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                clicBouton.play();
                timeline.stop();
                directionAlien=1;
                hauteurAliens=0;
                hauteurAliensL2=0;
                hauteurAliensL3=0;
                score = 0;
                entreeEnSceneLigne2=false;
                entreeEnSceneLigne3=false;
                perdu=false;
                gagne=false;
                modeInsane=false;
                vie=3;
                mediaplayer.stop(); //on éteint la musique
                start(primaryStage);
            }    
        });
        
        btnMenu.setFocusTraversable(false); //pour pas que quand le joueur tire (espace) cela clique sur le bouton

        
        root.getChildren().add(btnMenu);
        
        
        
        //affichage de l'ensemble
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static int distance(int x1, int y1, int x2, int y2){
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    public void run(){
        
        //On affiche un écran bleu
        gc.setFill(Color.ROYALBLUE); //couleur de fond
        gc.fillRect(0, 0, width, height);
                    
        //Gestion des mouvements aliens : 
        int positionXDernierAlien = aliens.get(4).getPosX();
        int positionXPremierAlien = aliens.get(0).getPosX();
        int positionYAliens = aliens.get(0).getPosY();       
        
        //DROITE
        if((positionXDernierAlien<450) && (directionAlien==1)){
            for(Alien mechant : aliens){
                mechant.moveRight();
            }
        }
        
        //On les fait déscendre si ils sont sur le bord droit: 
        if((positionXDernierAlien>=450) && (directionAlien==1)){
            directionAlien=0;
            hauteurAliens++;
            System.out.println(hauteurAliens);
            for(Alien mechant : aliens){
                mechant.descendre(positionYAliens+40); 
            }  
            Random rand = new Random();
            int nombreAleatoire = rand.nextInt(4 - 0 + 1) + 0;  
            
            //Gestion mode facile et insane 
            if(modeInsane){ //si on choisi le mode insane : on fait tirer tous les aliens avec uen vitesse de 20
                for(Alien virus : aliens){
                    tirsAlien.add(virus.tirer());
                }
                for(Tir tirMechant : tirsAlien){
                    tirMechant.setSpeedAlien(20);
                }
                sonTirAlien.play(); //son du tir alien
            }
            else{ //si on choisi le mode noormal : on ne fait tirer qu'un alien avec une vitesse de 10
                tirsAlien.add(aliens.get(nombreAleatoire).tirer()); 
                for(Tir tirMechant : tirsAlien){
                    tirMechant.setSpeedAlien(10);
                }
                sonTirAlien.play(); //son du tir alien
            }
            
        }
        
        //GAUCHE
        if((positionXPremierAlien>10) && (directionAlien==0)){
            for(Alien mechant : aliens){
                mechant.moveLeft();
            }
        }
        
        //On les fait déscendre si ils sont sur le bord gauche: 
        if((positionXPremierAlien<=10) && (directionAlien==0)){
            directionAlien=1;
            hauteurAliens++;
            System.out.println(hauteurAliens);
            for(Alien mechant : aliens){
                mechant.descendre(positionYAliens+45); 
            }
            Random rand = new Random();
            int nombreAleatoire = rand.nextInt(4 - 0 + 1) + 0;
            
            //Gestion mode facile et insane 
            if(modeInsane){ //si on choisi le mode insane : on fait tirer tous les aliens avec uen vitesse de 20
                for(Alien virus : aliens){
                    tirsAlien.add(virus.tirer());
                }
                for(Tir tirMechant : tirsAlien){
                    tirMechant.setSpeedAlien(20);
                }
                sonTirAlien.play(); //son du tir alien
            }
            else{ //si on choisi le mode noormal : on ne fait tirer qu'un alien avec une vitesse de 10
                tirsAlien.add(aliens.get(nombreAleatoire).tirer()); 
                for(Tir tirMechant : tirsAlien){
                    tirMechant.setSpeedAlien(10);
                }
                sonTirAlien.play(); //son du tir alien
            }
            
       
        }
               
        //Si les aliens atteignent le joueur : 
        for(Alien a : aliens){
            if(a.getIsDead()!=true){
                int dist = this.distance(joueur.posX, joueur.posY, a.posX, a.posY);
                if((dist>-35) && (dist<35)){
                    perdu=true;
                }
            }
        }
        
        for(Alien a : aliensL2){
            if(a.getIsDead()!=true){
                int dist = this.distance(joueur.posX, joueur.posY, a.posX, a.posY);
                if((dist>-35) && (dist<35)){
                    perdu=true;
                }
            }
        }
        
        for(Alien a : aliensL3){
            if(a.getIsDead()!=true){
                int dist = this.distance(joueur.posX, joueur.posY, a.posX, a.posY);
                if((dist>-35) && (dist<35)){
                    perdu=true;
                }
            }
        }
        //////////
        
        //Si les aliens sont morts, on les enleves de la liste : 
        for(int i = 0;i<aliens.size();i++){
            Alien mechantAlien = aliens.get(i);
            if(mechantAlien.getIsDead()){
                mechantAlien.setImage(new Image("file:images/isDead.png"));
            }
        }
        //Si les aliens de la ligne 2 sont morts, on les enleves de la liste : 
        for(int j = 0;j<aliensL2.size();j++){
            Alien mechantAlien = aliensL2.get(j);
            if(mechantAlien.getIsDead()){
                mechantAlien.setImage(new Image("file:images/isDead.png"));
            }
        }  
        //Si les aliens de la ligne 3 sont morts, on les enleves de la liste : 
        for(int k = 0;k<aliensL3.size();k++){
            Alien mechantAlien = aliensL3.get(k);
            if(mechantAlien.getIsDead()){
                mechantAlien.setImage(new Image("file:images/isDead.png"));
            }
        } 
                
        //affichage 2eme ligne : 
        if(hauteurAliens==1){            
            entreeEnSceneLigne2 = true;   
        }
        
        //affichage 3eme ligne : 
        if(hauteurAliensL2==1){            
            entreeEnSceneLigne3 = true;   
        }
        
        
        if(entreeEnSceneLigne2==true){ // Gestion Ligne 2 
            
            //Affichage de la deuxième ligne
            for(int i=0;i<aliensL2.size();i++){
                aliensL2.get(i).affiche(gc);
            }
            
            //Gestion des mouvements aliensL2 : 
            int positionXDernierAlienL2 = aliensL2.get(4).getPosX();
            int positionXPremierAlienL2 = aliensL2.get(0).getPosX();
            int positionYAliensL2 = aliensL2.get(0).getPosY();       
        
            //DROITE
            if((positionXDernierAlienL2<450) && (directionAlien==1)){
                for(Alien mechant : aliensL2){
                    mechant.moveRight();
                }
            }
        
            //On les fait déscendre si ils sont sur le bord droit: 
            if((positionXDernierAlienL2>=450) && (directionAlien==1)){
                hauteurAliensL2++;
                for(Alien mechant : aliensL2){
                    mechant.descendre(positionYAliensL2+40); 
                }
                aliensL2.get(0).setPosX(265);
                aliensL2.get(1).setPosX(310);
                aliensL2.get(2).setPosX(355);
                aliensL2.get(3).setPosX(400);
                aliensL2.get(4).setPosX(445);
            }
        
            //GAUCHE
            if((positionXPremierAlienL2>10) && (directionAlien==0)){
                for(Alien mechant : aliensL2){
                 mechant.moveLeft();
                }
            }
        
            //On les fait déscendre si ils sont sur le bord gauche: 
            if((positionXPremierAlienL2<=10) && (directionAlien==0)){
                hauteurAliensL2++;
                for(Alien mechant : aliensL2){
                    mechant.descendre(positionYAliensL2+45); 
                }
                aliensL2.get(0).setPosX(10);
                aliensL2.get(1).setPosX(55);
                aliensL2.get(2).setPosX(100);
                aliensL2.get(3).setPosX(145);
                aliensL2.get(4).setPosX(190);
            }
        }
        
        if(entreeEnSceneLigne3==true){ // Gestion Ligne 3 
            
            //Affichage de la deuxième ligne
            for(int i=0;i<aliensL3.size();i++){
                aliensL3.get(i).affiche(gc);
            }
            
            //Gestion des mouvements aliensL2 : 
            int positionXDernierAlienL3 = aliensL3.get(4).getPosX();
            int positionXPremierAlienL3 = aliensL3.get(0).getPosX();
            int positionYAliensL3 = aliensL3.get(0).getPosY();       
        
            //DROITE
            if((positionXDernierAlienL3<450) && (directionAlien==1)){
                for(Alien mechant : aliensL3){
                    mechant.moveRight();
                }
            }
        
            //On les fait déscendre si ils sont sur le bord droit: 
            if((positionXDernierAlienL3>=450) && (directionAlien==1)){
                hauteurAliensL3++;
                for(Alien mechant : aliensL3){
                    mechant.descendre(positionYAliensL3+40); 
                }
                aliensL3.get(0).setPosX(265);
                aliensL3.get(1).setPosX(310);
                aliensL3.get(2).setPosX(355);
                aliensL3.get(3).setPosX(400);
                aliensL3.get(4).setPosX(445);
            }
        
            //GAUCHE
            if((positionXPremierAlienL3>10) && (directionAlien==0)){
                for(Alien mechant : aliensL3){
                 mechant.moveLeft();
                }
            }
        
            //On les fait déscendre si ils sont sur le bord gauche: 
            if((positionXPremierAlienL3<=10) && (directionAlien==0)){
                hauteurAliensL3++;
                for(Alien mechant : aliensL3){
                    mechant.descendre(positionYAliensL3+45); 
                }
                aliensL3.get(0).setPosX(10);
                aliensL3.get(1).setPosX(55);
                aliensL3.get(2).setPosX(100);
                aliensL3.get(3).setPosX(145);
                aliensL3.get(4).setPosX(190);
            }
        }
                
        //affichage du score et de la vie : 
        gc.strokeText("score : "+score, 220, 15); 
        gc.strokeText("vie : "+vie, 450, 15); 
        
                
        //update des tirs joueur
        for(int i = 0;i<tirs.size();i++){
            Tir leTir = tirs.get(i);
            leTir.update();
            leTir.affiche(gc);
            if(leTir.getPosY()<=20){ // si le tire dépasse l'écran et ne peut plus toucher les aliens
                tirs.remove(leTir);
            }
            
            
            for(int j = 0;j<aliens.size();j++){ //Gestion des tirs
                Alien ali = aliens.get(j);
                if((leTir.collision(ali)) && (ali.getIsDead()!=true)){    
                    ali.setImage(new Image("file:images/explosion.png")); // on met l'image d'explosion à l'alen
                    sonAlienMeurt.play(); //son de l'alien qui meurt
                    ali.setIdDead(true); // on dit au jeu qu'il est mort
                    tirs.remove(leTir); //on retire le tir de la liste des tirs
                    score+=10; // on met à jour le score
                }
            }
            
            for(int k = 0;k<aliensL2.size();k++){ //Gestion des tirs
                Alien ali = aliensL2.get(k);
                if((leTir.collision(ali)) && (ali.getIsDead()!=true)){    
                    ali.setImage(new Image("file:images/explosion.png")); // on met l'image d'explosion à l'alen
                    sonAlienMeurt.play(); //son de l'alien qui meurt
                    ali.setIdDead(true); // on dit au jeu qu'il est mort
                    tirs.remove(leTir); //on retire le tir de la liste des tirs
                    score+=10; // on met à jour le score
                }
            }
            
            for(int l = 0;l<aliensL3.size();l++){ //Gestion des tirs
                Alien ali = aliensL3.get(l);
                if((leTir.collision(ali)) && (ali.getIsDead()!=true)){    
                    ali.setImage(new Image("file:images/explosion.png")); // on met l'image d'explosion à l'alen
                    sonAlienMeurt.play(); //son de l'alien qui meurt
                    ali.setIdDead(true); // on dit au jeu qu'il est mort
                    tirs.remove(leTir); //on retire le tir de la liste des tirs
                    score+=10; // on met à jour le score
                }
            }
            
        }
                
        //update des tirs aliens
        for(int i = 0;i<tirsAlien.size();i++){
            tirsAlien.get(i).updateAlien();
            tirsAlien.get(i).affiche(gc);
            int dist = this.distance(joueur.posX, joueur.posY, tirsAlien.get(i).getPosX(), tirsAlien.get(i).getPosY());
            if((dist>-20) && (dist<30)){
                vie--;
                sonJoueurMeurt.play();
                if(vie==0){
                    perdu=true;
                }
                tirsAlien.remove(tirsAlien.get(i));
            }
            if(tirsAlien.get(i).getPosY()>=380){ // si le tire dépasse l'écran et ne peut plus toucher les aliens
                tirsAlien.remove(tirsAlien.get(i));
            }
        }
        
        //Affichage des aliens
        for(int i = 0;i<aliens.size();i++){
            Alien al = aliens.get(i);
            al.affiche(gc);
        }
        // Affichage du joueur
        joueur.affiche(gc);
        
        //gestion game over : 
        if(perdu){
            timeline.stop();
            gc.drawImage(new Image("file:images/gameOver.png"), 0, 0, 500, 400);
            gc.strokeText("score : "+score, 220, 15);
            sonPerdu.play();
        }
               
        //gestion nbAliens : 
        int nbMortsL1=0;
        int nbMortsL2=0;
        int nbMortsL3=0;
        
        for(int i=0;i<aliens.size();i++){
            if(aliens.get(i).getIsDead()){
                nbMortsL1++;
            }
        }
        for(int i=0;i<aliensL2.size();i++){
            if(aliensL2.get(i).getIsDead()){
                nbMortsL2++;
            }
        }   
        for(int i=0;i<aliensL3.size();i++){
            if(aliensL3.get(i).getIsDead()){
                nbMortsL3++;
            }
        }
        
        if((nbMortsL1==5) && (nbMortsL2==5) && (nbMortsL3==5)){
            gagne=true;
        }
        
        if(gagne){
            timeline.stop();
            gc.drawImage(new Image("file:images/gameOverWin.png"), 0, 0, 500, 400);
            gc.strokeText("score : "+score, 220, 15);
            sonGagne.play();
        }
    }
    
    public void menuInformation(Stage primaryStage) {
	canvas = new Canvas(width, height);
        root = new Pane(canvas);
        root.setPrefSize(width, height);
        root.setStyle("-fx-background-image: url('file:images/backgroundMenu.png');");
        scene = new Scene(root);
        gc = canvas.getGraphicsContext2D();
    
        gc.setFont(new Font("TimesRoman", 45));
        gc.strokeText("Space Invaders", 10, 50);
        gc.setFont(new Font("TimesRoman", 15));
        gc.strokeText("Application JavaFx codée par :", 10,140);
        gc.strokeText("EL RHOUFI Mohamed et DOUSSELAERE Maxime ", 10,160);
        gc.strokeText("dans le cadre d'un projet pour l'EILCO", 10,180);
  
        gc.setFont(new Font("TimesRoman", 20));
        gc.strokeText("Synopsis : ", 10,250);
        gc.setFont(new Font("TimesRoman", 15));
        gc.strokeText("Un virus extraterrestre s'approche de la terre, ", 10,275);
        gc.strokeText("votre vaisseau est la dernière barrière !", 10,295 );
	
        Button btnMenu = new Button();
        btnMenu.setLayoutX(200);
        btnMenu.setLayoutY(325);
        btnMenu.setText("Retour au menu");
        btnMenu.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                mediaplayer.stop(); //on éteint la musique
                clicBouton.play();
        	start(primaryStage);
        }    });
    root.getChildren().add(btnMenu);
    primaryStage.setTitle("Space Invaders");
    primaryStage.setScene(scene);
    primaryStage.show();
	
    }
    
    public void menuSettings(Stage primaryStage){
 	canvas = new Canvas(width, height);
        root = new Pane(canvas);
        root.setPrefSize(width, height);
        root.setStyle("-fx-background-image: url('file:images/backgroundMenu.png');");
        scene = new Scene(root);
        gc = canvas.getGraphicsContext2D();
    
        gc.setFont(new Font("TimesRoman", 45));
        gc.strokeText("Space Invaders", 10, 50);
        gc.setFont(new Font("TimesRoman", 15));
        gc.strokeText("Contrôles :", 10,100);
        gc.strokeText("Gauche..............Flèche de gauche", 10,120);
        gc.strokeText("Droite.....................Flèche de droite", 10,140);
        gc.strokeText("Tirer...........................................Espace", 10,160);
  
        gc.strokeText("Musique et sons :", 10,200);
        
        //btn Activer la musique : 
        Button btnActiveMusique = new Button();
        btnActiveMusique.setLayoutX(10);
        btnActiveMusique.setLayoutY(210);
        btnActiveMusique.setText("Activer musique");
        btnActiveMusique.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                clicBouton.play();
                volumeMusique=0.5;
                mediaplayer.setVolume(volumeMusique);
        }    });
        root.getChildren().add(btnActiveMusique);
        //btn désactiver musique : 
        Button btnDesActiveMusique = new Button();
        btnDesActiveMusique.setLayoutX(10);
        btnDesActiveMusique.setLayoutY(240);
        btnDesActiveMusique.setText("Désactiver musique");
        btnDesActiveMusique.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                clicBouton.play();
                volumeMusique=0;
                mediaplayer.stop();
        }    });
        root.getChildren().add(btnDesActiveMusique);
        
        //btn activer sons : 
        Button btnActiveSons = new Button();
        btnActiveSons.setLayoutX(10);
        btnActiveSons.setLayoutY(270);
        btnActiveSons.setText("Activer sons");
        btnActiveSons.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                clicBouton.play();
                volumeSons=0.5;
                sonTirJoueur.setVolume(volumeSons);
                sonAlienMeurt.setVolume(volumeSons);
                sonJoueurMeurt.setVolume(volumeSons);
                sonTirAlien.setVolume(volumeSons);
                
        }    });
        root.getChildren().add(btnActiveSons);
        
        //btn désactiver sons : 
        Button btnDesActiveSons = new Button();
        btnDesActiveSons.setLayoutX(10);
        btnDesActiveSons.setLayoutY(300);
        btnDesActiveSons.setText("Désactiver sons");
        btnDesActiveSons.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                clicBouton.play();
                volumeSons=0;
                sonTirJoueur.setVolume(volumeSons);
                sonAlienMeurt.setVolume(volumeSons);
                sonJoueurMeurt.setVolume(volumeSons);
                sonTirAlien.setVolume(volumeSons);
                
        }    });
        root.getChildren().add(btnDesActiveSons);
	
        Button btnMenu = new Button();
        btnMenu.setLayoutX(200);
        btnMenu.setLayoutY(325);
        btnMenu.setText("Sauvegarder");
        btnMenu.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent arg0) {
                clicBouton.play();
                mediaplayer.stop(); //on éteint la musique
        	start(primaryStage);
        }    });
        root.getChildren().add(btnMenu);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();       
    }
}