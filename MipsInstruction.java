package main;

import main.Main.RegistersEnum;

/**
 * Class to represent a MIPS instruction. Stores the number of parameters for
 * the instruction, the parameters, and a skeleton string for the instruction.
 */
public class MipsInstruction {
    private int _opCode = 0;
    private int _funcCode = 0;
    private short _numOfParams = 3;
    private Object[] _params = new String[3];
    private String _skeleton = "% % % %";
    private boolean _hasFuncCode, _hasSource, _hasSource2, _hasDestination, _hasConstant,
            _hasJumpLabel, _hasOffset, _hasJumpRegister;
    private String _instrName;

    /**
     * Default constructor
     */
    public MipsInstruction() {
    }

    /**
     * Constructor that accepts the number of parameters for the instruction,
     * the parameters, the skeleton, the operation code and the function code.
     * 
     * @param instrName The name of the instruction.
     * @param numOfParams The number of parameter for the instruction.
     * @param params The variable parameters for the instruction.
     * @param skeleton The skeleton structure for the instruction.
     * @param opCode The operation code.
     * @param funcCode The function code.
     * @param hasFuncCode Whether or not the instruction has a function code.
     */
    public MipsInstruction(String instrName, short numOfParams, Object[] params, String skeleton,
            int opCode, int funcCode, boolean hasFuncCode) {
        _instrName = instrName;
        _numOfParams = numOfParams;
        _params = params;
        _skeleton = skeleton;
        _opCode = opCode;
        _funcCode = funcCode;
        _hasFuncCode = hasFuncCode;
    }

    /**
     * Constructor that accepts the number of parameters for the instruction,
     * the parameters, and the skeleton.
     * 
     * @param numOfParams The number of parameter for the instruction.
     * @param params The variable parameters for the instruction.
     * @param skeleton The skeleton structure for the instruction.
     */
    public MipsInstruction(short numOfParams, String[] params, String skeleton) {
        _numOfParams = numOfParams;
        _params = params;
        _skeleton = skeleton;
    }

    /**
     * Constructor that accepts the number of parameters for the instruction,
     * the parameters, the skeleton and the function code.
     * 
     * @param numOfParams The number of parameter for the instruction.
     * @param params The variable parameters for the instruction.
     * @param skeleton The skeleton structure for the instruction.
     * @param funcCode The function code.
     */
    public MipsInstruction(short numOfParams, String[] params, String skeleton, int funcCode,
            boolean hasFuncCode) {
        _numOfParams = numOfParams;
        _params = params;
        _skeleton = skeleton;
        _funcCode = funcCode;
        _hasFuncCode = hasFuncCode;
    }

    /**
     * Get the number of parameters for the instruction.
     * 
     * @return The number of parameters for this instruction.
     */
    public int getNumOfParams() {
        return _numOfParams;
    }

    /**
     * Set the number of parameters.
     * 
     * @param numOfParams The number of parameters to set.
     */
    public void setNumOfParams(short numOfParams) {
        _numOfParams = numOfParams;
    }

    /**
     * Get the parameters for the instruction.
     * 
     * @return The parameters for the instruction
     */
    public Object[] getParams() {
        return _params;
    }

    /**
     * Set the parameters for the instruction.
     * 
     * @param params The parameters to set.
     */
    public void setParms(String[] params) {
        _params = params;
    }

    /**
     * Get the skeleton structure for the mips instruction.
     * 
     * @return The skeleton structure.
     */
    public String getSkeleton() {
        return _skeleton;
    }

    /**
     * Set the skeleton structure for the mips instruction.
     * 
     * @param skeleton The skeleton structure to set.
     */
    public void setSkeleton(String skeleton) {
        _skeleton = skeleton;
    }

    /**
     * Get the operation code.
     * 
     * @return The operation code.
     */
    public int getOpCode() {
        return _opCode;
    }

    /**
     * Set the operation code.
     * 
     * @param opCode The operation code to set.
     */
    public void setOpCode(short opCode) {
        _opCode = opCode;
    }

    /**
     * Get the function code.
     * 
     * @return The function code.
     */
    public int getFuncCode() {
        return _funcCode;
    }

    /**
     * Set the function code.
     * 
     * @param funcCode The function code to set.
     */
    public void setFuncCode(short funcCode) {
        _funcCode = funcCode;
    }

    /**
     * Determine if the mips instruction has a function code.
     * 
     * @return True if the mips instruction has a function code.
     */
    public boolean hasFuncCode() {
        return _hasFuncCode;
    }

    /**
     * Set the hasFuncCode flag.
     * 
     * @param hasFuncCode The value to set.
     */
    public void setHasFuncCode(boolean hasFuncCode) {
        _hasFuncCode = hasFuncCode;
    }

    /**
     * Determine if the mips instruction has a source register.
     * 
     * @return True if the mips instruction has a source register.
     */
    public boolean hasSource() {
        return _hasSource;
    }

    /**
     * Set the hasSource flag.
     * 
     * @param hasSource The value to set.
     */
    public void setHasSource(boolean hasSource) {
        _hasSource = hasSource;
    }

    /**
     * Determine if the mips instruction has a source register.
     * 
     * @return True if the mips instruction has a source register.
     */
    public boolean hasSource2() {
        return _hasSource2;
    }

    /**
     * Set the hasSource2 flag.
     * 
     * @param hasSource2 The value to set.
     */
    public void setHasSource2(boolean hasSource2) {
        _hasSource2 = hasSource2;
    }

    /**
     * Determine if the mips instruction has a source register.
     * 
     * @return True if the mips instruction has a source register.
     */
    public boolean hasDestination() {
        return _hasDestination;
    }

    /**
     * Set the hasDestination flag.
     * 
     * @param hasDestination The value to set.
     */
    public void setHasDestination(boolean hasDestination) {
        _hasDestination = hasDestination;
    }

    /**
     * Determine if the mips instruction has a constant to add.
     * 
     * @return True if the mips instruction has a constant to add.
     */
    public boolean hasConstant() {
        return _hasConstant;
    }

    /**
     * Set the hasSource flag.
     * 
     * @param hasConstant The value to set.
     */
    public void setHasConstant(boolean hasConstant) {
        _hasConstant = hasConstant;
    }

    /**
     * Set the instruction name.
     * 
     * @param instrName The instruction name to set.
     */
    public void setInstructionName(String instrName) {
        _instrName = instrName;
    }

    /**
     * Get the instruction name.
     * 
     * @return the instruction name.
     */
    public String getInstructionName() {
        return _instrName;
    }

    /**
     * Determine if the mips instruction has a jump label.
     * 
     * @return True if the mips instruction has a jump label.
     */
    public boolean hasJumpLabel() {
        return _hasJumpLabel;
    }

    /**
     * Set the hasJumpLabel flag.
     * 
     * @param hasJumpLabel The value to set.
     */
    public void setHasJumpLabel(boolean hasJumpLabel) {
        _hasJumpLabel = hasJumpLabel;
    }

    /**
     * Determine if the instruction has an offset, e.g. loadword and store word
     * 
     * @return True if the instruction has an offset
     */
    public boolean hasOffset() {
        return _hasOffset;
    }

    /**
     * Set the has jump register flag.
     * 
     * @param hasJumpRegister The value to set.
     */
    public void setHasJumpRegister(boolean hasJumpRegister) {
        _hasOffset = hasJumpRegister;
    }

    /**
     * Determine if the instruction has a jump register, e.g. jr
     * 
     * @return True if the instruction has a jump register
     */
    public boolean hasJumpRegister() {
        return _hasJumpRegister;
    }

    /**
     * Set the hasOffset flag.
     * 
     * @param hasOffset The value to set.
     */
    public void setHasOffset(boolean hasOffset) {
        _hasOffset = hasOffset;
    }

    /**
     * Build an instruction using the parameters and the skeleton. Also tests
     * that there are enough parameters to build the instruction.
     * 
     * @param arguments The instruction parameters.
     * @return The instruction filled with the parameters.
     * @throws Exception When the instruction was not passed the appropriate
     *         number of parameters.
     */
    public String buildInstruction(Object[] arguments) throws Exception {
        if (_numOfParams != arguments.length) {
            throw new Exception(String.format(
                    "Invalid number of parameters. The %s instruction "
                            + "requires %d parameters but was provided %d.",
                    _instrName, _numOfParams, arguments.length));
        }
        return String.format(_skeleton, arguments);
    }

    /**
     * Convert the instruction to hex machine code.
     * Process each part of an instruction as necessary, converting the
     * string/number into a decimal value, and adding to the total decimal value
     * for the instruction. Lastly convert the final value to a hex value in the
     * form of a string and return it.
     * 
     * @return The instruction in hex machine code.
     */
    public String convertInstructionToHex(long labelAddr) {
        long instrDecimal = 0L;

        instrDecimal += _opCode;

        if (_hasFuncCode) {
            instrDecimal += _funcCode;
        }

        if (_hasDestination) {
            instrDecimal += RegistersEnum.valueOf(_params[0].toString()).getRegNum();
        }
        if (_hasSource) {
            instrDecimal += RegistersEnum.valueOf(_params[1].toString()).getRegNum();
        }
        if (_hasSource2) {
            instrDecimal += RegistersEnum.valueOf(_params[2].toString()).getRegNum();
        }
        if (_hasConstant) {
            // constant is always last argument, first cast to string, then
            // parse long in case of a large constant
            instrDecimal += Long.parseLong(_params[_params.length - 1].toString());
        }
        if (_hasJumpLabel) {
            instrDecimal += labelAddr;
        }
        if (_hasOffset) {
            // offset is always 2nd argument for lw and sw
            instrDecimal += Long.parseLong(_params[1].toString());
        }
        if (_hasJumpRegister) {
            // if jumping a register, it's the only param, just retrieve it's
            // number from the enum
            instrDecimal += RegistersEnum.valueOf(_params[0].toString()).getRegNum();
        }
        return Long.toHexString(instrDecimal);
    }
}
