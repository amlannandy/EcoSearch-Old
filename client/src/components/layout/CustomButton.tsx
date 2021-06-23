import React, { MouseEventHandler } from 'react';
import withStyles, { WithStylesProps } from 'react-jss';

const styles = {
  button: {
    color: 'white',
    backgroundColor: '#581b98',
    padding: 10,
    borderRadius: 5,
    border: 'none',
    cursor: 'pointer',
    minWidth: 370,
    shadow: '1px black',
  },
  primary: {},
  danger: {
    backgroundColor: 'red',
  },
  success: {
    backgroundColor: 'green',
  },
};

interface CustomButtonProps extends WithStylesProps<typeof styles> {
  text: string;
  onPress: MouseEventHandler;
  type: String;
}

const CustomButton = ({ classes, text, onPress, type }: CustomButtonProps) => {
  return (
    <button className={classes.button} onClick={onPress}>
      {text}
    </button>
  );
};

export default withStyles(styles)(CustomButton);
