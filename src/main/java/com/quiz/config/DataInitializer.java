package com.quiz.config;

import com.quiz.model.Category;
import com.quiz.model.Difficulty;
import com.quiz.model.Question;
import com.quiz.model.User;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        createAdminIfNeeded();
        createQuestionsIfNeeded();
    }

    private void createAdminIfNeeded() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setDisplayName("Administrateur");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println(">>> Compte admin créé : username=admin / password=admin123");
        }
    }

    private void createQuestionsIfNeeded() {
        if (questionRepository.count() > 0) return;

        // ===== ANGLAIS - FACILE =====
        save("What is the past tense of 'go'?",
            List.of("goed", "went", "gone", "going"),
            1, "The past tense of 'go' is 'went' (irregular verb).",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("Choose the correct article: '___ apple a day keeps the doctor away.'",
            List.of("A", "An", "The", "No article"),
            1, "'An' is used before words starting with a vowel sound like 'apple'.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("What does 'beautiful' mean in French?",
            List.of("laid", "beau/belle", "grand", "petit"),
            1, "'Beautiful' means 'beau' (masculine) or 'belle' (feminine) in French.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("Which word is a synonym of 'happy'?",
            List.of("sad", "angry", "joyful", "tired"),
            2, "'Joyful' is a synonym of 'happy', both meaning feeling pleasure.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("Fill in: 'She ___ to school every day.'",
            List.of("go", "goes", "going", "gone"),
            1, "With third person singular (she/he/it), we add 's' to the verb.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("What is the plural of 'child'?",
            List.of("childs", "childes", "children", "childre"),
            2, "'Children' is the irregular plural of 'child'.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        save("Which is the correct spelling?",
            List.of("recieve", "receive", "recive", "receeve"),
            1, "The correct spelling is 'receive' - remember: i before e except after c.",
            Category.ANGLAIS, Difficulty.FACILE, 1);

        // ===== ANGLAIS - MOYEN =====
        save("Choose the correct form: 'If I ___ rich, I would travel the world.'",
            List.of("am", "was", "were", "be"),
            2, "In conditional type 2, we use 'were' for all subjects (I/he/she were).",
            Category.ANGLAIS, Difficulty.MOYEN, 2);

        save("What is the correct passive form of 'They built the bridge in 1950'?",
            List.of("The bridge was built in 1950", "The bridge built in 1950", "The bridge is built in 1950", "The bridge has built in 1950"),
            0, "Passive voice: subject + was/were + past participle.",
            Category.ANGLAIS, Difficulty.MOYEN, 2);

        save("Which sentence uses the present perfect correctly?",
            List.of("I have went to Paris last year", "I have been to Paris", "I have go to Paris", "I was going to Paris"),
            1, "'I have been to Paris' correctly uses present perfect (have + past participle).",
            Category.ANGLAIS, Difficulty.MOYEN, 2);

        save("What is the meaning of the idiom 'break a leg'?",
            List.of("Get injured", "Good luck", "Run fast", "Dance well"),
            1, "'Break a leg' is a theatrical idiom meaning 'good luck'.",
            Category.ANGLAIS, Difficulty.MOYEN, 2);

        save("Identify the grammatical error: 'Neither the students nor the teacher were ready.'",
            List.of("Neither should be either", "Teacher should be teachers", "Were should be was", "No error"),
            2, "With 'neither...nor', the verb agrees with the nearest subject (teacher = singular = was).",
            Category.ANGLAIS, Difficulty.MOYEN, 2);

        // ===== ANGLAIS - DIFFICILE =====
        save("What literary device is used in: 'The wind whispered through the trees'?",
            List.of("Metaphor", "Simile", "Personification", "Alliteration"),
            2, "Personification gives human qualities (whispering) to non-human things (wind).",
            Category.ANGLAIS, Difficulty.DIFFICILE, 3);

        save("Which word has the same root as 'philanthropy'?",
            List.of("philosopher", "misanthrope", "anthropology", "philharmonic"),
            2, "'Anthropology' shares the Greek root 'anthropo' meaning human being.",
            Category.ANGLAIS, Difficulty.DIFFICILE, 3);

        save("What is a 'dangling modifier'?",
            List.of("An adjective used incorrectly", "A modifier that doesn't clearly modify the right word", "A verb in wrong tense", "An unnecessary comma"),
            1, "A dangling modifier is a phrase that doesn't clearly modify the intended word in a sentence.",
            Category.ANGLAIS, Difficulty.DIFFICILE, 3);

        // ===== MATHS - FACILE =====
        save("Combien font 7 x 8 ?",
            List.of("54", "56", "64", "48"),
            1, "7 x 8 = 56",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Quel est le resultat de 144 / 12 ?",
            List.of("10", "11", "12", "13"),
            2, "144 / 12 = 12",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Quel est le perimetre d'un carre de cote 5 cm ?",
            List.of("10 cm", "15 cm", "20 cm", "25 cm"),
            2, "Perimetre d'un carre = 4 x cote = 4 x 5 = 20 cm",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Combien y a-t-il de degres dans un angle droit ?",
            List.of("45", "90", "180", "360"),
            1, "Un angle droit mesure exactement 90 degres.",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Quelle fraction est equivalente a 0.5 ?",
            List.of("1/3", "2/5", "1/2", "3/4"),
            2, "0.5 = 1/2 (un demi)",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Quel est le resultat de 2 puissance 3 ?",
            List.of("6", "8", "9", "12"),
            1, "2^3 = 2 x 2 x 2 = 8",
            Category.MATHS, Difficulty.FACILE, 1);

        save("Quel nombre est premier ?",
            List.of("9", "15", "21", "17"),
            3, "17 est un nombre premier (divisible uniquement par 1 et 17).",
            Category.MATHS, Difficulty.FACILE, 1);

        // ===== MATHS - MOYEN =====
        save("Resoudre : 2x + 5 = 13. Que vaut x ?",
            List.of("3", "4", "5", "9"),
            1, "2x = 13 - 5 = 8, donc x = 8/2 = 4",
            Category.MATHS, Difficulty.MOYEN, 2);

        save("Quelle est l'aire d'un cercle de rayon 7 cm ? (pi = 3.14)",
            List.of("43.96 cm2", "153.86 cm2", "21.98 cm2", "49 cm2"),
            1, "Aire = pi x r2 = 3.14 x 49 = 153.86 cm2",
            Category.MATHS, Difficulty.MOYEN, 2);

        save("Quel est le PGCD de 48 et 36 ?",
            List.of("6", "9", "12", "18"),
            2, "Le plus grand commun diviseur de 48 et 36 est 12.",
            Category.MATHS, Difficulty.MOYEN, 2);

        save("Factoriser : x2 - 9",
            List.of("(x-3)2", "(x+3)(x-3)", "(x-9)(x+1)", "(x+3)2"),
            1, "x2 - 9 = x2 - 32 = (x+3)(x-3)",
            Category.MATHS, Difficulty.MOYEN, 2);

        save("Dans un triangle rectangle, si les deux cotes sont 3 et 4, quelle est l'hypothenuse ?",
            List.of("5", "6", "7", "racine de 7"),
            0, "Theoreme de Pythagore : c2 = 32 + 42 = 9 + 16 = 25, donc c = 5",
            Category.MATHS, Difficulty.MOYEN, 2);

        // ===== MATHS - DIFFICILE =====
        save("Calculer la derivee de f(x) = 3x3 - 2x2 + 5x - 1",
            List.of("9x2 - 4x + 5", "3x2 - 2x + 5", "9x2 + 4x - 5", "6x2 - 4x + 5"),
            0, "f'(x) = 3x3x2 - 2x2x + 5 = 9x2 - 4x + 5",
            Category.MATHS, Difficulty.DIFFICILE, 3);

        save("Quelle est la limite de sin(x)/x quand x tend vers 0 ?",
            List.of("0", "infini", "1", "indefinie"),
            2, "La limite remarquable : lim(x->0) sin(x)/x = 1",
            Category.MATHS, Difficulty.DIFFICILE, 3);

        save("Qu'est-ce que l'integrale de e puissance x ?",
            List.of("e^x + C", "x*e^x + C", "e^x/x + C", "ln(x) + C"),
            0, "L'integrale de e^x est e^x + C (e^x est sa propre derivee et integrale)",
            Category.MATHS, Difficulty.DIFFICILE, 3);

        // ===== CHIMIE - FACILE =====
        save("Quelle est la formule chimique de l'eau ?",
            List.of("HO", "H2O", "H3O", "OH2"),
            1, "L'eau est composee de 2 atomes d'hydrogene et 1 atome d'oxygene : H2O",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Quel est le symbole chimique de l'or ?",
            List.of("Or", "Go", "Au", "Ag"),
            2, "Le symbole de l'or est Au, du latin 'Aurum'.",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Combien d'elements y a-t-il dans le tableau periodique ?",
            List.of("92", "108", "118", "120"),
            2, "Le tableau periodique contient actuellement 118 elements officiellement reconnus.",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Quel gaz est produit lors de la photosynthese ?",
            List.of("CO2", "N2", "O2", "H2"),
            2, "Les plantes produisent de l'oxygene (O2) lors de la photosynthese.",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Quel est le numero atomique du carbone ?",
            List.of("4", "6", "8", "12"),
            1, "Le carbone a le numero atomique 6 (6 protons dans le noyau).",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Quelle est la formule du dioxyde de carbone ?",
            List.of("CO", "CO2", "C2O", "C2O2"),
            1, "Le dioxyde de carbone est CO2 (1 carbone + 2 oxygenes).",
            Category.CHIMIE, Difficulty.FACILE, 1);

        save("Quel est le pH d'une solution neutre ?",
            List.of("0", "5", "7", "14"),
            2, "Une solution neutre a un pH de 7 (ni acide, ni basique).",
            Category.CHIMIE, Difficulty.FACILE, 1);

        // ===== CHIMIE - MOYEN =====
        save("Quel type de liaison forme l'eau entre ses molecules ?",
            List.of("Liaison ionique", "Liaison covalente", "Liaison hydrogene", "Liaison metallique"),
            2, "Les molecules d'eau s'attirent par des liaisons hydrogene.",
            Category.CHIMIE, Difficulty.MOYEN, 2);

        save("Quelle est la masse molaire de H2SO4 ? (H=1, S=32, O=16)",
            List.of("64 g/mol", "80 g/mol", "96 g/mol", "98 g/mol"),
            3, "H2SO4 : 2x1 + 32 + 4x16 = 2 + 32 + 64 = 98 g/mol",
            Category.CHIMIE, Difficulty.MOYEN, 2);

        save("Lors d'une reaction acide-base, qu'est-ce qui est transfere ?",
            List.of("Electrons", "Neutrons", "Protons H+", "Atomes d'oxygene"),
            2, "Selon Bronsted-Lowry, une reaction acide-base implique le transfert d'un proton H+.",
            Category.CHIMIE, Difficulty.MOYEN, 2);

        // ===== CHIMIE - DIFFICILE =====
        save("Quelle est la loi de Hess ?",
            List.of("L'enthalpie d'une reaction est independante du chemin reactionnel",
                    "La pression est inversement proportionnelle au volume",
                    "La vitesse de reaction double tous les 10C",
                    "L'energie libre est nulle a l'equilibre"),
            0, "La loi de Hess : l'enthalpie totale est la meme quel que soit le chemin suivi.",
            Category.CHIMIE, Difficulty.DIFFICILE, 3);

        save("Quel est le mecanisme de la reaction SN2 ?",
            List.of("Substitution avec carbocation intermediaire",
                    "Substitution en une etape avec inversion de configuration",
                    "Substitution avec radical libre",
                    "Substitution avec retention de configuration"),
            1, "SN2 : Substitution Nucleophile bimoleculaire, en une etape avec inversion de Walden.",
            Category.CHIMIE, Difficulty.DIFFICILE, 3);

        save("Quelle orbitale hybride forme le methane CH4 ?",
            List.of("sp", "sp2", "sp3", "sp3d"),
            2, "Le carbone dans CH4 est hybride sp3 (4 liaisons sigma, angle de 109.5 degres).",
            Category.CHIMIE, Difficulty.DIFFICILE, 3);
    }

    private void save(String text, List<String> options, int correctIndex, String explanation,
                      Category category, Difficulty difficulty, int points) {
        Question q = new Question();
        q.setText(text);
        q.setOptions(options);
        q.setCorrectAnswerIndex(correctIndex);
        q.setExplanation(explanation);
        q.setCategory(category);
        q.setDifficulty(difficulty);
        q.setPoints(points);
        questionRepository.save(q);
    }
}
