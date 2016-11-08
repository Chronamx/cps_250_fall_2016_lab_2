package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Marcus Chronabery/Garrett O'Grady
 * CPS 250 Section 1
 * Prof. Zargham
 * 10/22/2016
 * Lab # 2
 * This program's purpose is to read a file containing MIPS instructions,
 * process the MIPS code and return byte code.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Map<String, Integer> _labelMap = new HashMap<String, Integer>();
    private static final Map<String, List<MipsInstruction>> _pseudoInsMap =
            new HashMap<String, List<MipsInstruction>>();
    private static final Map<String, List<MipsInstruction>> _coreInsMap =
            new HashMap<String, List<MipsInstruction>>();

    /**
     * Main method. Accepts 1 and only 1 argument, the input file name.
     * 
     * @param args The command line arguments.
     * @throws Exception When an invalid number of arguments are passed. 1 and
     *         only 1 accepted.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1 || args[0] == null) {
            throw new Exception(
                    "Invalid arguments! You need to provide just the name of the input file");
        }
        BufferedReader inStream = null;
        try {
            buildCoreInstructionMap();
            buildPseudoInstructionMap();
            inStream = new BufferedReader(new FileReader(new File(args[0])));
            processLabels(inStream);
            inStream.close();
            // System.exit(0);
            inStream = new BufferedReader(new FileReader(new File(args[0])));
            processInstructions(inStream);
        }
        catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "File not found! Exiting program", ex);
            System.exit(0);
        }
        catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    "Exception thrown while reading from the file! Exiting program", ex);
            System.exit(0);
        }
        catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage() + " Exiting program.");
            System.exit(0);
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage() + " Exiting program.");
            System.exit(0);
        }
        finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    /**
     * Build a map containing core instructions, where the key is the command,
     * and the value is a mips instruction containing the arguments, a
     * skeleton instruction, the op code, and function code if it has one.
     */
    private static void buildCoreInstructionMap() {
        // add immediate instruction
        _coreInsMap.put("addi", new ArrayList<>());
        _coreInsMap.get("addi").add(new MipsInstruction("addi", (short) 3,
                new Object[] { "%s", "%s", "%d" }, "addi %s, %s, %d", 8, 0, false));
        MipsInstruction ins = _coreInsMap.get("addi").get(0);
        ins.setHasSource(true);
        ins.setHasConstant(true);
        ins.setHasDestination(true);

        // load word instruction
        _coreInsMap.put("lw", new ArrayList<>());
        _coreInsMap.get("lw").add(new MipsInstruction("lw", (short) 3,
                new Object[] { "%s", "%d", "%s" }, "lw %s, %d(%s)", 0x23, 0, false));
        ins = _coreInsMap.get("lw").get(0);
        ins.setHasSource(true);
        ins.setHasOffset(true);
        ins.setHasDestination(true);

        // load word instruction
        _coreInsMap.put("sw", new ArrayList<>());
        _coreInsMap.get("sw").add(new MipsInstruction("sw", (short) 3,
                new Object[] { "%s", "%d", "%s" }, "sw %s, %d(%s)", 0x2b, 0, false));
        ins = _coreInsMap.get("sw").get(0);
        ins.setHasSource(true);
        ins.setHasOffset(true);
        ins.setHasDestination(true);

        // jump and link instruction
        _coreInsMap.put("jal", new ArrayList<>());
        _coreInsMap.get("jal").add(new MipsInstruction("jal", (short) 1, new Object[] { "%s" },
                "jal %s", 3, 0, false));
        ins = _coreInsMap.get("jal").get(0);
        ins.setHasJumpLabel(true);

        // jump register instruction
        _coreInsMap.put("jr", new ArrayList<>());
        _coreInsMap.get("jr").add(
                new MipsInstruction("jr", (short) 1, new Object[] { "%s" }, "jr %s", 0, 8, true));
        ins = _coreInsMap.get("jr").get(0);
        ins.setHasJumpRegister(true);

        // set less than immediate instruction
        _coreInsMap.put("slti", new ArrayList<>());
        _coreInsMap.get("slti").add(new MipsInstruction("slti", (short) 3,
                new Object[] { "%s, %s, %d" }, "slti %s, %s, %d", 0xa, 0, false));
        ins = _coreInsMap.get("slti").get(0);
        ins.setHasSource(true);
        ins.setHasConstant(true);
        ins.setHasDestination(true);

        // branch on equal instruction
        _coreInsMap.put("beq", new ArrayList<>());
        _coreInsMap.get("beq").add(new MipsInstruction("beq", (short) 3,
                new Object[] { "%s, %s, %s" }, "beq %s, %s, %s", 4, 0, false));
        ins = _coreInsMap.get("beq").get(0);
        ins.setHasSource(true);
        ins.setHasJumpLabel(true);
        ins.setHasDestination(true);

        // load upper immediate instruction, part of the li psuedo instruction
        _coreInsMap.put("lui", new ArrayList<>());
        _coreInsMap.get("lui").add(new MipsInstruction("lui", (short) 2,
                new String[] { "%s", "%s" }, "lui %s, %s", 0xf, 0, false));
        ins = _coreInsMap.get("lui").get(0);
        ins.setHasDestination(true);
        ins.setHasConstant(true);

        // or immediate instruction, part of the li psuedo instruction
        _coreInsMap.put("ori", new ArrayList<>());
        _coreInsMap.get("ori").add(new MipsInstruction((short) 2, new String[] { "%s", "%s", "%d" },
                "ori %s, %s, %s"));
        ins = _coreInsMap.get("ori").get(0);
        ins.setHasConstant(true);
        ins.setHasDestination(true);
        ins.setHasSource(true);

        // multiply instruction, part of the mul psuedo instruction
        _coreInsMap.put("mult", new ArrayList<>());
        _coreInsMap.get("mult").add(new MipsInstruction("mult", (short) 2,
                new String[] { "%s", "%s" }, "mult %s, %s", 0, 0x18, true));
        ins = _coreInsMap.get("mult").get(0);
        ins.setHasSource(true);
        ins.setHasSource2(true);

        // move from lo, part of the mul psuedo instruction
        _coreInsMap.put("mflo", new ArrayList<>());
        _coreInsMap.get("mflo").add(new MipsInstruction("mflo", (short) 1, new String[] { "%s" },
                "mflo %s", 0, 0x12, true));
        ins = _coreInsMap.get("mflo").get(0);
        ins.setHasDestination(true);
    }

    /**
     * Read through the file and process each instruction, this time ignoring
     * labels.
     * 
     * @param inStream A buffered input stream used to read instructions from a
     *        text file.
     * @throws IOException When an error occurs during reading or writing of a
     *         file.
     * @throws IllegalArgumentException When an instruction is not found or the
     *         syntax is incorrect.
     */
    private static void processInstructions(BufferedReader inStream)
            throws IOException, IllegalArgumentException {
        BufferedWriter bw = null;
        try {
            String line = inStream.readLine().trim();
            bw = new BufferedWriter(new FileWriter(new File("result.txt")));
            String instruction = "";
            List<MipsInstruction> inst = null;
            // while not e.o.f
            while (line != null) {
                // see if the line is a label by itself, if so continue reading
                if (line.equals("") || line.matches(".+[:]+")) {
                    line = inStream.readLine().trim();
                    continue;
                }
                // gets instruction if label in on the same line - ex: loop:
                // addi $s1, $s0, $s3
                else if (line.contains(":")) {
                    instruction = line.substring(line.indexOf(':') + 1).trim();
                }
                else {
                    instruction = line;
                }
                String instructionArg = instruction.split(" ")[0];
                String mipsCode =
                        instruction.substring(line.indexOf(' ') + 1).replaceAll("\\s+", "");

                if (_pseudoInsMap.containsKey(instructionArg)) {
                    // set variable equal to instruction
                    inst = _pseudoInsMap.get(instructionArg);
                }
                else if (_coreInsMap.containsKey(instructionArg)) {
                    inst = _coreInsMap.get(instructionArg);
                }
                else {
                    throw new IllegalArgumentException(
                            "The instruction was not found or is not implemented in this project!");
                }

                ArrayList<String> split = new ArrayList<String>();
                // split the arguments on a comma
                split.addAll(Arrays.asList(mipsCode.split(",")));

                for (MipsInstruction instr : inst) {
                    // if the arguments list size doesn't match the expected
                    // number of params
                    if (split.size() != instr.getNumOfParams()) {
                        for (String argument : split) {
                            if (argument.contains("(")) {
                                split.remove(argument);
                                String offSet = argument.substring(argument.indexOf("(") + 1,
                                        argument.indexOf(")") + 1);
                                String src = argument.substring(argument.indexOf(")") + 1);
                                split.add(offSet);
                                split.add(src);
                            }
                        }
                        if (split.size() != instr.getNumOfParams()) {
                            throw new IllegalArgumentException(
                                    String.format("Error processing line: %s", line));
                        }
                    }
                    instr.setParms((String[]) split.toArray());
                    if (instr.hasJumpLabel()) {
                        // if the instruction has a jump label, retrieve the
                        // label from the map using the first instruction
                        // argument
                        bw.write(instr.convertInstructionToHex(_labelMap.get(split.get(0))));
                    }
                    else {
                        bw.write(instr.convertInstructionToHex(0));
                    }
                }
            }
        }
        finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * Build a map such that the key is a psuedo instruction, and the value
     * contains the instructions necessary to complete the task.
     */
    private static void buildPseudoInstructionMap() {
        // move instruction
        _pseudoInsMap.put("move", new ArrayList<>());
        _pseudoInsMap.get("move").add(_coreInsMap.get("addi").get(0));

        // load immediate instruction
        _pseudoInsMap.put("li", new ArrayList<>());
        _pseudoInsMap.get("li").add(_coreInsMap.get("lui").get(0));
        _pseudoInsMap.get("li").add(_coreInsMap.get("ori").get(0));

        // multiply instruction
        _pseudoInsMap.put("mul", new ArrayList<>());
        _pseudoInsMap.get("mul").add(_coreInsMap.get("mult").get(0));
        _pseudoInsMap.get("mul").add(_coreInsMap.get("mflo").get(0));
    }

    /**
     * Read through the file line by line and store any labels in a HashMap,
     * where the key is the label, and the value is the memory location (in
     * decimal) assigned to the instruction.
     * 
     * @param inStream A buffered reader used to read the contents of the file.
     * @throws IOException When an error occurs during file reading.
     */
    private static void processLabels(BufferedReader inStream) throws IOException {
        String line = inStream.readLine().trim();
        int prgmCtr = 0;
        while (line != null) {
            final int lblInd = line.indexOf(":");
            // if the line contains a semi-colon, it has a label
            if (lblInd > 0) {
                // store label, without beginning or ending whitespace
                _labelMap.put(line.substring(0, lblInd).trim(), prgmCtr);
            }
            // read next line
            line = inStream.readLine().trim();
            // increment program counter
            prgmCtr += 4;
        }
    }

    /**
     * Enumeration for registers. Name and number are stored.
     */
    public enum RegistersEnum {

        ZERO("$zero", (byte) 0),
        V0("$v0", (byte) 2),
        V1("$v1", (byte) 3),
        A0("$a0", (byte) 4),
        A1("$a1", (byte) 5),
        A2("$a2", (byte) 6),
        A3("$a3", (byte) 7),
        T0("$t0", (byte) 8),
        T1("$t1", (byte) 9),
        T2("$t2", (byte) 10),
        T3("$t3", (byte) 11),
        T4("$t4", (byte) 12),
        T5("$t5", (byte) 13),
        T6("$t6", (byte) 14),
        T7("$t7", (byte) 15),
        S0("$s0", (byte) 16),
        S1("$s1", (byte) 17),
        S2("$s2", (byte) 18),
        S3("$s3", (byte) 19),
        S4("$s4", (byte) 20),
        S5("$s5", (byte) 21),
        S6("$s6", (byte) 22),
        S7("$s7", (byte) 23),
        T8("$t8", (byte) 24),
        T9("$t9", (byte) 25),
        GP("$gp", (byte) 28),
        SP("$sp", (byte) 29),
        FP("$fp", (byte) 30),
        RA("$ra", (byte) 31);

        private final String _regName;
        private final byte _regNum;

        RegistersEnum(String regName, byte regNum) {
            _regName = regName;
            _regNum = regNum;
        }

        /**
         * Get the register name.
         * 
         * @return The register name.
         */
        public String getRegName() {
            return _regName;
        }

        /**
         * Get the register number
         * 
         * @return The register number.
         */
        public byte getRegNum() {
            return _regNum;
        }
    }
}
