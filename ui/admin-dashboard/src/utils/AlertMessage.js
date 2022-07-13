function children(props, ref) {
    useImperativeHandle(ref, () => ({
        hello: () => console.log('hello world!')
    }));
    return <h1>children</h1>;
}
export default forwardRef(children);
