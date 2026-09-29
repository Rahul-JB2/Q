package com.example.data.local

import com.example.data.model.ChapterItem
import com.example.data.model.TestItem
import java.time.LocalDate
import java.time.ZoneOffset

object PreloadedData {

    private fun parseDateToMillis(isoDate: String): Long {
        return try {
            LocalDate.parse(isoDate).atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000L
        } catch (_: Exception) {
            System.currentTimeMillis()
        }
    }

    val defaultTests = listOf(
        TestItem(
            id = 1,
            testNumber = 1,
            testName = "PART TEST-1",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "04-Oct-2026",
            epochDateMillis = parseDateToMillis("2026-10-04"),
            physicsSyllabus = "Units and Measurements, Motion in a Straight Line, Motion in a Plane, Laws of Motion, Vectors, Vernier calliper and screw gauge",
            mathSyllabus = "Vector Algebra + 3D Geometry + Probability",
            pchemSyllabus = "Stoichiometry-I, Stoichiometry-II, Atomic Structure",
            ichemSyllabus = "Periodic Table (Main), Periodic Table, S-block and Hydrogen (Adv)",
            ochemSyllabus = "-",
            cumulativeNote = "Initial Foundation Test"
        ),
        TestItem(
            id = 2,
            testNumber = 2,
            testName = "PART TEST-2",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "11-Oct-2026",
            epochDateMillis = parseDateToMillis("2026-10-11"),
            physicsSyllabus = "Work, Energy, and Power, System of Particles and Rotational Motion, Gravitation (+ Part Test-1)",
            mathSyllabus = "Relations & Functions + Inverse Trigonometric Functions + Matrices + Determinants (+ Part Test-1)",
            pchemSyllabus = "Thermodynamics, Thermochemistry, Gaseous State (Adv) (+ Part Test-1)",
            ichemSyllabus = "Chemical Bonding (+ Part Test-1)",
            ochemSyllabus = "-",
            cumulativeNote = "Cumulative revision of Part Test-1 syllabus"
        ),
        TestItem(
            id = 3,
            testNumber = 3,
            testName = "PART TEST-3",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "25-Oct-2026",
            epochDateMillis = parseDateToMillis("2026-10-25"),
            physicsSyllabus = "Mechanical Properties of Solids, Mechanical Properties of Fluids, Oscillations (+ Part Test-1, 2)",
            mathSyllabus = "Permutation & Combination + Binomial Theorem + Statistics (+ Part Test-1, 2)",
            pchemSyllabus = "Part Test-1, 2 revision",
            ichemSyllabus = "Part Test-1, 2 revision",
            ochemSyllabus = "GOC, Carbocation, Nucleophilic Substitution and Elimination, Aromatic Compounds",
            cumulativeNote = "Cumulative revision of Part Test-1 and Part Test-2"
        ),
        TestItem(
            id = 4,
            testNumber = 4,
            testName = "PART TEST-4",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "01-Nov-2026",
            epochDateMillis = parseDateToMillis("2026-11-01"),
            physicsSyllabus = "Thermal Properties of Matter, Thermodynamics, Kinetic Theory of Gases, Waves (+ Part Test-1 to 3)",
            mathSyllabus = "Straight Line + Circle + Parabola + Ellipse + Hyperbola (+ Part Test-1 to 3)",
            pchemSyllabus = "Chemical Equilibrium, Ionic Equilibrium (+ Part Test-1 to 3)",
            ichemSyllabus = "Coordination Compound (+ Part Test-1 to 3)",
            ochemSyllabus = "Part Test-3 revision",
            cumulativeNote = "Cumulative revision of Part Test-1 to 3"
        ),
        TestItem(
            id = 5,
            testNumber = 5,
            testName = "PART TEST-5",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "22-Nov-2026",
            epochDateMillis = parseDateToMillis("2026-11-22"),
            physicsSyllabus = "Electric Charges and Fields, Electrostatic Potential and Capacitance, Current Electricity (+ Part Test-1 to 4)",
            mathSyllabus = "Trigonometric Ratios and Identities, Trigonometric Equation, Height and Distance + Complex Numbers (+ Part Test-1 to 4)",
            pchemSyllabus = "Part Test-1 to 4 revision",
            ichemSyllabus = "Part Test-1 to 4 revision",
            ochemSyllabus = "Carbanion, Enolate Ion, Oxidation and Reduction, Biomolecules and POC (+ Part Test-3, 4)",
            cumulativeNote = "Cumulative revision of Part Test-1 to 4"
        ),
        TestItem(
            id = 6,
            testNumber = 6,
            testName = "PART TEST-6",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "29-Nov-2026",
            epochDateMillis = parseDateToMillis("2026-11-29"),
            physicsSyllabus = "Moving Charges and Magnetism, Magnetism and Matter, Electromagnetic Induction, Alternating Current (+ Part Test-1 to 5)",
            mathSyllabus = "Limits + Continuity + Differentiability + Application of Derivatives (+ Part Test-1 to 5)",
            pchemSyllabus = "Chemical Kinetics, Liquid Solution, Surface Chemistry (Adv) (+ Part Test-1 to 5)",
            ichemSyllabus = "Group-13, 14, 15 & 16 (+ Part Test-1 to 5)",
            ochemSyllabus = "Part Test-3 to 5 revision",
            cumulativeNote = "Cumulative revision of Part Test-1 to 5"
        ),
        TestItem(
            id = 7,
            testNumber = 7,
            testName = "PART TEST-7",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "06-Dec-2026",
            epochDateMillis = parseDateToMillis("2026-12-06"),
            physicsSyllabus = "Electromagnetic Waves, Ray Optics and Optical Instruments, Wave Optics (+ Part Test-1 to 6)",
            mathSyllabus = "Indefinite Integration + Definite Integration + Area + Differential Equations (+ Part Test-1 to 6)",
            pchemSyllabus = "Part Test-1 to 6 revision",
            ichemSyllabus = "D & F-Block (+ Part Test-1 to 6)",
            ochemSyllabus = "Nomenclature, Isomerism, Free Radical, Carbene and Nitrene (+ Part Test-3 to 5)",
            cumulativeNote = "Cumulative revision of Part Test-1 to 6"
        ),
        TestItem(
            id = 8,
            testNumber = 8,
            testName = "PART TEST-8",
            testType = "PART_TEST",
            pattern = "JEE MAIN & JEE ADV",
            examMode = "Offline",
            testDate = "13-Dec-2026",
            epochDateMillis = parseDateToMillis("2026-12-13"),
            physicsSyllabus = "Dual Nature of Radiation and Matter, Atoms, Nuclei, Semiconductor Electronics (+ Part Test 1 to 7)",
            mathSyllabus = "Quadratic Equations + Sequence & Series (+ Part Test 1 to 7)",
            pchemSyllabus = "Electrochemistry, Solid State (Adv) (+ Part Test-1 to 7)",
            ichemSyllabus = "Metallurgy and Salt Analysis (Adv) (+ Part Test-1 to 7)",
            ochemSyllabus = "Part Test 1 to 7 revision",
            cumulativeNote = "Grand Part Test (All Part Tests 1 to 7 cumulative)"
        ),
        // FULL SYLLABUS TESTS (Dec 2026 - Jan 2027)
        TestItem(
            id = 9,
            testNumber = 101,
            testName = "FULL TEST-1",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "20-Dec-2026",
            epochDateMillis = parseDateToMillis("2026-12-20"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 1"
        ),
        TestItem(
            id = 10,
            testNumber = 102,
            testName = "FULL TEST-2",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "CBT",
            testDate = "27-Dec-2026",
            epochDateMillis = parseDateToMillis("2026-12-27"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 2 (Computer Based Test Mode)"
        ),
        TestItem(
            id = 11,
            testNumber = 103,
            testName = "FULL TEST-3",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "01-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-01"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "New Year Special Full Mock"
        ),
        TestItem(
            id = 12,
            testNumber = 104,
            testName = "FULL TEST-4",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "03-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-03"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 4"
        ),
        TestItem(
            id = 13,
            testNumber = 105,
            testName = "FULL TEST-5",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "05-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-05"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 5"
        ),
        TestItem(
            id = 14,
            testNumber = 106,
            testName = "FULL TEST-6",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "CBT",
            testDate = "07-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-07"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 6 (Computer Based Test Mode)"
        ),
        TestItem(
            id = 15,
            testNumber = 107,
            testName = "FULL TEST-7",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "09-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-09"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 7"
        ),
        TestItem(
            id = 16,
            testNumber = 108,
            testName = "FULL TEST-8",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "11-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-11"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 8"
        ),
        TestItem(
            id = 17,
            testNumber = 109,
            testName = "FULL TEST-9",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "13-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-13"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 9"
        ),
        TestItem(
            id = 18,
            testNumber = 110,
            testName = "FULL TEST-10",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "15-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-15"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Full Mock 10"
        ),
        TestItem(
            id = 19,
            testNumber = 111,
            testName = "FULL TEST-11",
            testType = "FULL_TEST",
            pattern = "JEE MAIN",
            examMode = "Offline",
            testDate = "17-Jan-2027",
            epochDateMillis = parseDateToMillis("2027-01-17"),
            physicsSyllabus = "Full Syllabus",
            mathSyllabus = "Full Syllabus",
            pchemSyllabus = "Full Syllabus",
            ichemSyllabus = "Full Syllabus",
            ochemSyllabus = "Full Syllabus",
            cumulativeNote = "Final Grand Full Mock before JEE Main Session 1"
        )
    )

    val defaultChapters = listOf(
        // ================= PART TEST 1 CHAPTERS =================
        // Physics PT-1
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Units, Dimensions & Measurements",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Error propagation: Z = A*B => dZ/Z = dA/A + dB/B. For Z = A^p B^q / C^r => dZ/Z = p(dA/A) + q(dB/B) + r(dC/C). Standard dimensions: [G]=M^-1 L^3 T^-2, [h]=M L^2 T^-1, [Permeability]=M L T^-2 A^-2.",
            notesSummary = "Vernier/Screw Gauge + Percentage Error rules"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Motion in a Straight Line (1D)",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "v = u + at, s = ut + 0.5at^2, v^2 = u^2 + 2as. Distance in nth sec: s_n = u + (a/2)(2n-1). Stop distance d = u^2 / (2a). Relative velocity v_AB = v_A - v_B. Area under v-t graph = Displacement.",
            notesSummary = "1D kinematics & relative speed in straight motion"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Motion in a Plane & Vectors (2D)",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Trajectory: y = x*tan(theta) - g*x^2 / (2u^2*cos^2(theta)) = x*tan(theta)(1 - x/R). Range R = (u^2*sin(2*theta))/g, H_max = (u^2*sin^2(theta))/(2g), Time T = (2u*sin(theta))/g. For complimentary angles, R_1 = R_2, R = 4*sqrt(H1*H2).",
            notesSummary = "Projectile & river-boat / rain-man vectors"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Laws of Motion & Friction",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "F_net = dp/dt = ma. Static friction f_s <= mu_s * N, kinetic f_k = mu_k * N. Angle of repose theta = tan^-1(mu). Safe turn on banked road: v = sqrt(r*g*(tan(theta) + mu)/(1 - mu*tan(theta))). Pulley constraint: sum(T . a) = 0.",
            notesSummary = "FBD, friction limits, banking & pseudo force"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Vernier Calliper & Screw Gauge",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Vernier LC = 1 MSD - 1 VSD = (1 - m/n) MSD. Total reading = MSR + (VSR * LC) - Zero Error. Screw Gauge LC = Pitch / Total Circular Divisions. Pitch = Distance moved in n rotations / n. True reading = Observed - (±Zero Error).",
            notesSummary = "Experimental physics high-scoring formula sheet"
        ),
        // Math PT-1
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Vector Algebra",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Dot: a.b = |a||b|cos(theta). Projection of a on b = (a.b)/|b|. Cross: |axb| = |a||b|sin(theta). Area of triangle = 0.5|axb|, parallelogram = |axb| or 0.5|d1 x d2|. Scalar triple: [a b c] = a.(b x c) = vol of parallelepiped. Vector triple: a x (b x c) = (a.c)b - (a.b)c.",
            notesSummary = "Dot, cross, STP and VTP expansions"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Three Dimensional Geometry (3D)",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Line: (x-x1)/a = (y-y1)/b = (z-z1)/c. Shortest distance between skew lines = |(a2 - a1) . (b1 x b2)| / |b1 x b2|. Plane: ax + by + cz + d = 0. Distance from (x0,y0,z0) = |ax0+by0+cz0+d| / sqrt(a^2+b^2+c^2). Line of intersection & coplanarity condition.",
            notesSummary = "Direction cosines, line-plane shortest distance"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Probability",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Conditional: P(A|B) = P(A cap B)/P(B). Bayes' theorem: P(E_i|A) = [P(E_i)P(A|E_i)] / sum[P(E_j)P(A|E_j)]. Binomial Distribution: P(X=r) = nCr * p^r * q^(n-r), Mean = np, Variance = npq. Total probability rule.",
            notesSummary = "Bayes Theorem, independent events & distributions"
        ),
        // Chem PT-1
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Stoichiometry-I (Mole Concept)",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "n = Weight/MolarMass = V(L at STP)/22.4 = N/N_A. Molarity M = n_solute / V_solution(L). Molality m = n_solute / W_solvent(kg). Mole fraction X_A + X_B = 1. Dilution M1V1 = M2V2. Limiting reagent check: moles / stoichiometric coefficient.",
            notesSummary = "Concentration terms, limiting reagent & empirical formula"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Stoichiometry-II (Redox Reactions)",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Equivalent weight = Molar mass / n-factor. Normality N = M * n-factor. For KMnO4: acidic n=5 (Mn2+), neutral n=3 (MnO2), basic n=1 (MnO4 2-). For K2Cr2O7 in acidic medium n=6 (Cr3+). Equivalents: N1V1 = N2V2.",
            notesSummary = "Oxidation number, n-factor & titration equivalence"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Atomic Structure",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Bohr radius r_n = 0.529 * (n^2/Z) A. Energy E_n = -13.6 * (Z^2/n^2) eV. Rydberg 1/lambda = R_H * Z^2 * (1/n1^2 - 1/n2^2). de Broglie lambda = h/p = h/sqrt(2mE). Heisenberg delta_x * delta_p >= h / (4*pi). Quantum numbers n, l, m_l, s.",
            notesSummary = "Bohr model, de Broglie, orbital nodes & quantum numbers"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "Periodic Table & Periodic Properties",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Slater's rules: Z_eff = Z - sigma. Atomic radius: down group increases, across period decreases (except noble gases Van der Waals radius max). Ionization Enthalpy anomalies: Be > B (penetration of 2s), N > O (half-filled 2p3 stability). Electron Gain Enthalpy: Cl > F, S > O.",
            notesSummary = "Periodic trends, IE & EA exceptions, electronegativity"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "S-Block Elements & Hydrogen",
            partTestId = 1,
            isCumulativeInTests = "PT-1 to PT-8, Full Tests",
            formulaSheetSnippet = "Hydration enthalpy: Li+ > Na+ > K+ > Rb+ > Cs+; Ionic mobility order in aq: Cs+ > Rb+ > K+ > Na+ > Li+. Flame colors: Li (Crimson), Na (Golden yellow), K (Violet), Ca (Brick red), Sr (Crimson), Ba (Apple green). Liquid ammonia solution: blue due to ammoniated electrons.",
            notesSummary = "Alkali & alkaline earth metals, anomalous Li & Be, H2O2"
        ),

        // ================= PART TEST 2 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Work, Energy, and Power",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "W = integral F.dr = delta K (Work-Energy Theorem). Conservative force: F = -dU/dx. Potential energy U = -integral F.dr. Equilibrium: dU/dx = 0; stable d^2U/dx^2 > 0, unstable < 0. Power P = dW/dt = F.v. Vertical circle critical speed: top v = sqrt(gR), bottom v = sqrt(5gR).",
            notesSummary = "Work energy theorem, vertical circle & power"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "System of Particles & Rotational Motion",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "Centre of mass R_cm = sum(m_i r_i)/M. Torque tau = r x F = I*alpha. Angular momentum L = r x p = I*omega. Parallel axis: I = I_cm + M*d^2. Perpendicular axis: I_z = I_x + I_y (planar bodies). Pure rolling: v_cm = R*omega, a_cm = R*alpha. Total KE = 0.5*M*v^2*(1 + k^2/R^2).",
            notesSummary = "MI theorems, angular momentum conservation & rolling"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Gravitation",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "F = G*M*m/r^2. g at height h: g_h = g(1 - 2h/R) (for h<<R) or g*R^2/(R+h)^2. g at depth d: g_d = g(1 - d/R). Gravitational potential V = -GM/r. Escape velocity v_e = sqrt(2GM/R) = sqrt(2gR). Orbital velocity v_o = sqrt(GM/r). Kepler's 3rd: T^2 proportional to r^3.",
            notesSummary = "Kepler laws, g variation with height/depth, escape velocity"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Relations & Functions",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "Reflexive (a,a in R), Symmetric (a,b in R => b,a in R), Transitive (a,b & b,c in R => a,c in R). One-one (Injective): f(x1)=f(x2) => x1=x2 or f'(x)>0 / f'(x)<0 strictly. Onto (Surjective): Range = Codomain. Bijection = One-one + Onto. Number of onto functions from n to m elements.",
            notesSummary = "Equivalence relations, domain/range, injective/surjective"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Inverse Trigonometric Functions (ITF)",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "Principal domains: sin^-1 [-pi/2, pi/2], cos^-1 [0, pi], tan^-1 (-pi/2, pi/2). sin^-1(x) + cos^-1(x) = pi/2, tan^-1(x) + cot^-1(x) = pi/2. tan^-1(x) + tan^-1(y) = tan^-1((x+y)/(1-xy)) for xy < 1. 2tan^-1(x) = sin^-1(2x/(1+x^2)) = cos^-1((1-x^2)/(1+x^2)).",
            notesSummary = "Principal branches, summation formulas & conversion identities"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Matrices & Determinants",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "A*adj(A) = |A|*I. |adj(A)| = |A|^(n-1). |adj(adj(A))| = |A|^((n-1)^2). A^-1 = adj(A)/|A|. System of linear equations AX = B: Cramer's rule, unique solution if |A| != 0; infinitely many if |A|=0 and (adj A)B = 0; no solution if |A|=0 and (adj A)B != 0.",
            notesSummary = "Adjoint properties, inverse, symmetric/skew-symmetric & Cramer rule"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Thermodynamics & Thermochemistry",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "First Law: delta U = q + w. Isothermal reversible work w = -2.303 n R T log(V2/V1). Adiabatic reversible: T V^(gamma-1) = const, P V^gamma = const. Enthalpy H = U + PV, delta H = delta U + delta n_g R T. Entropy delta S = q_rev / T. Gibbs: delta G = delta H - T delta S. At equilibrium delta G = 0, delta G^o = -RT ln(K).",
            notesSummary = "First & second law, work formulas, Hess law & delta G"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Gaseous State",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "Ideal: PV = nRT. Van der Waals: (P + a*n^2/V^2)(V - n*b) = nRT. Compressibility factor Z = PV/(nRT). Critical constants: T_c = 8a/(27Rb), P_c = a/(27b^2), V_c = 3b. Z_c = 3/8 = 0.375. Graham's law r1/r2 = sqrt(M2/M1). RMS speed v_rms = sqrt(3RT/M), average = sqrt(8RT/(pi M)), most probable = sqrt(2RT/M).",
            notesSummary = "Real gas deviations, Van der Waals constants & molecular speeds"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "Chemical Bonding & Molecular Structure",
            partTestId = 2,
            isCumulativeInTests = "PT-2 to PT-8, Full Tests",
            formulaSheetSnippet = "VSEPR & Steric Number = (Valence e on central atom + Monovalent atoms - Charge)/2. Hybridization: sp, sp2, sp3, sp3d, sp3d2. MOT: Bond Order = 0.5 * (N_b - N_a). O2 is paramagnetic (2 unpaired e in pi*2px, pi*2py). Dipole moment: NH3 > NF3. Hydrogen bonding: intermolecular vs intramolecular.",
            notesSummary = "Hybridization, VSEPR shapes, MOT configuration & dipole moments"
        ),

        // ================= PART TEST 3 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Mechanical Properties of Solids (Elasticity)",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Mechanical Properties of Fluids (Fluids)",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Oscillations (SHM)",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Permutations & Combinations (P&C)",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Binomial Theorem",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Statistics",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "General Organic Chemistry (GOC)",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Carbocation, Nucleophilic Sub & Elimination",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Aromatic Compounds",
            partTestId = 3,
            isCumulativeInTests = "PT-3 to PT-8, Full Tests"
        ),

        // ================= PART TEST 4 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Thermal Properties of Matter & Calorimetry",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Thermodynamics & Kinetic Theory (KTG)",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Waves & Sound Waves",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Straight Lines & Circle",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Conic Sections (Parabola, Ellipse, Hyperbola)",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Chemical Equilibrium & Ionic Equilibrium",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "Coordination Compounds",
            partTestId = 4,
            isCumulativeInTests = "PT-4 to PT-8, Full Tests"
        ),

        // ================= PART TEST 5 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Electric Charges and Fields",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Electrostatic Potential and Capacitance",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Current Electricity",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Trigonometric Ratios, Identities & Equations",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Heights & Distances + Complex Numbers",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Carbanion, Enolate Ion & Carbonyl Chemistry",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Oxidation and Reduction in Organic Chemistry",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Biomolecules and Practical Organic Chemistry (POC)",
            partTestId = 5,
            isCumulativeInTests = "PT-5 to PT-8, Full Tests"
        ),

        // ================= PART TEST 6 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Moving Charges and Magnetism",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Magnetism and Matter",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Electromagnetic Induction (EMI) & AC",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Limits, Continuity & Differentiability",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Application of Derivatives (AOD)",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Chemical Kinetics & Liquid Solutions",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Surface Chemistry (Adv)",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "P-Block Elements (Group 13, 14, 15, 16)",
            partTestId = 6,
            isCumulativeInTests = "PT-6 to PT-8, Full Tests"
        ),

        // ================= PART TEST 7 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Electromagnetic Waves (EM Waves)",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Ray Optics and Optical Instruments",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Wave Optics",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Indefinite & Definite Integration",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Area Under Curves & Differential Equations",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "IUPAC Nomenclature & Isomerism",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "O_CHEM",
            chapterName = "Free Radical, Carbene and Nitrene Intermediates",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "D & F-Block Elements",
            partTestId = 7,
            isCumulativeInTests = "PT-7 to PT-8, Full Tests"
        ),

        // ================= PART TEST 8 CHAPTERS =================
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Dual Nature of Radiation & Matter",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Atoms & Nuclei",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "PHYSICS",
            subSubject = "PHYSICS",
            chapterName = "Semiconductor Electronics",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Quadratic Equations",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "MATHEMATICS",
            subSubject = "MATH",
            chapterName = "Sequence & Series (AP, GP, HP)",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Electrochemistry",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "P_CHEM",
            chapterName = "Solid State (Adv)",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        ),
        ChapterItem(
            subject = "CHEMISTRY",
            subSubject = "I_CHEM",
            chapterName = "Metallurgy and Salt Analysis (Adv)",
            partTestId = 8,
            isCumulativeInTests = "PT-8, Full Tests"
        )
    )
}
