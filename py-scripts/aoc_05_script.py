import os.path



def main():
    dir_path = os.path.abspath(os.path.dirname(__file__))
    in_path = os.path.join(dir_path, "../input-05")
    out_path = os.path.join(dir_path, "../output-05_py")

    in_file = open(in_path)
    out_file = open(out_path, "w+")

    out_file.write(in_file.read())

    in_file.close()
    out_file.close()




















if __name__ == "__main__":
    main()