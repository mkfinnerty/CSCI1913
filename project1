class Random:
    def __init__(self, seed):
        self.begin = seed
    def next(self, range):
        self.begin = (((7**5)*(self.begin))%((2**31)-1))
        return self.begin%range
    def choose(self, characters):
        m = len(characters)
        num = self.next(m)
        return characters[num]
class Words:
    def __init__(self, seed):
        self.first = ""
        self.follow = {}
        self.random = Random(seed)
    def add(self, word):
        self.first = self.first + word[0]
        i = 0
        for i in range(len(word)-1):
            if word[i] in self.follow:
                self.follow[word[i]] += word[i+1]
            else:
                self.follow[word[i]] = word[i+1]
    def make(self, size):
        wordo = ""
        wordo = wordo + self.random.choose(self.first)
        while len(wordo) < size:
            n = wordo[-1]
            if n in self.follow:
                wordo = wordo + self.random.choose(self.follow[n])
            else:
                wordo = wordo + self.random.choose(self.first)
        return wordo
